package go.id.dinkesjatengprov.ilikes.ui.activity.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.AccountModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.helper.parser.isLowerContains
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class LoginViewModel(application: Application, repository: MainRepository) :
    BaseViewModel(application, repository) {

    val account = MutableLiveData<StatusRequestModel<AccountModel>>()

    fun login(phone: String, password: String) {
        account.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.authLogin(phone, password),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    val accountModel = data as AccountModel

                    repository.getSharedPrefManager().isLoggedIn = true
                    repository.getSharedPrefManager().token = accountModel.token

                    account.postValue(StatusRequestModel.success(accountModel))
                }

                override fun failure(failureModel: FailureModel) {
                    val model = validationError(failureModel)
                    account.postValue(StatusRequestModel.error(model))
                }
            })
    }

    /**
     * Menerjemahkan error dari pesan error yang diberikan server
     * @param failureModel pesan error yang diberikan server
     * */
    fun validationError(failureModel: FailureModel): FailureModel {
        var failureMessage = failureModel
        if (failureModel.msgSystem.isLowerContains("no rows in result set")) failureMessage =
            failureModel.copy(msgShow = "Akun tidak ditemukan. Mohon untuk mendaftar terlebih dahulu")
        if (failureModel.msgSystem.isLowerContains("crypto/bcrypt: hashedPassword is not the hash of the given password")) failureMessage =
            failureModel.copy(msgShow = "Kata sandi salah. Silahkan coba lagi")
        return failureMessage
    }


}