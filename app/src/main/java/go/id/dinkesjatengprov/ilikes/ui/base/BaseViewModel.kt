package go.id.dinkesjatengprov.ilikes.ui.base

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.AccountModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiResponseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.ResponseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener2
import go.id.dinkesjatengprov.ilikes.helper.TAG_API_REQUEST_FAILED
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import retrofit2.HttpException

abstract class BaseViewModel(val app: Application, val repository: MainRepository) :
    AndroidViewModel(app) {

    fun <T> requestFlowable(
        flowable: Flowable<T>,
        listener: RetrofitListener2
    ) {

        CompositeDisposable().add(
            flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSubscriber<T>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: T?) {
                        listener.next(t)
                    }

                    override fun onError(t: Throwable?) {
                        val error = parseThrowable<T>(t!!)
                        listener.failure(error)
                    }
                })
        )

    }

    fun <T> pmiRequestObservable(
        observable: Observable<PmiResponseModel<T>>,
        listener: RetrofitListener
    ) {
        CompositeDisposable().add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener.success(it.data)
                }, {
                    val error = FailureModel(
                        msgShow = it.message
                    )
                    listener.failure(error)
                })
        )
    }

    fun <T> request(
        observable: Observable<T>,
        listener: RetrofitListener
    ) {
        CompositeDisposable().add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener.success(it)
                }, {
                    val error = parseThrowable<T>(it)
                    listener.failure(error)
                })
        )
    }

    fun <T> requestObservable(
        observable: Observable<ResponseModel<T>>,
        listener: RetrofitListener
    ) {
        CompositeDisposable().add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener.success(it.data)
                }, {
                    val error = parseThrowable<T>(it)
                    listener.failure(error)
                })
        )
    }

    fun <T> requestSingle(single: Single<ResponseModel<T>>, listener: RetrofitListener2) {
        CompositeDisposable().add(
            single
                .doOnSuccess { listener.success(it.data) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // Request success, return the result
                    listener.next(it.data)
                }, {
                    val error = parseThrowable<T>(it)
                    listener.failure(error)
                })
        )
    }

    private fun <T> parseThrowable(throwable: Throwable): FailureModel {
        when (throwable) {
            /**
             *   Check is HttpException or not
             * */
            is HttpException -> {
                try {
                    /**
                     * Try to parse response to ResponseModel
                     * */
                    val body = throwable.response()?.errorBody()
                    val json = body?.string()

                    val model = Gson().fromJson<ResponseModel<T>>(
                        json,
                        ResponseModel::class.java
                    )
                    val error = failure(
                        TAG_API_REQUEST_FAILED + " " + throwable.code(),
                        model.error,
                        model.error,
                        throwable
                    )
                    return error
                } catch (e: Exception) {
                    val error = failure(
                        TAG_API_REQUEST_FAILED,
                        throwable.message,
                        e.message,
                        throwable
                    )
                    return error
                }
            }
            else -> {
                val error = failure(
                    TAG_API_REQUEST_FAILED,
                    throwable.message,
                    throwable.message,
                    throwable
                )
                return error
            }
        }
    }

    /**
     * @return FailureModel
     * */
    private fun failure(
        code: String?,
        msgShow: String?,
        msgSystem: String?,
        throwable: Throwable?
    ): FailureModel {
        return FailureModel(code, msgShow, msgSystem, throwable)
    }

    fun isLoggedIn(): Boolean {
        return repository.getSharedPrefManager().isLoggedIn
    }

    fun logout() {
        repository.logout()

        val context = app.applicationContext
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun logoutPeli() {
        repository.logoutPeli()

        val context = app.applicationContext
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun decodeJWT(jwt: String): AccountModel {
        val example =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJKb2huIERvZSIsIm5hbWUiOiJKb2huIERvZSIsImVtYWlsIjoiam9obmRvZUBtYWlsLmNvbSIsImdyb3VwIjoiYWRtaW4iLCJpYXQiOjE1MTYyMzkwMjJ9.V7PePUOtw7mIA3PWZtFYCGaBr9jhgo3vrMw9BOnE0U"
        val decode = JWT(example)

        return AccountModel(
            token = jwt,
            name = decode.claims["name"]?.asString(),
            username = decode.claims["username"]?.asString(),
            email = decode.claims["email"]?.asString(),
        )
    }

}