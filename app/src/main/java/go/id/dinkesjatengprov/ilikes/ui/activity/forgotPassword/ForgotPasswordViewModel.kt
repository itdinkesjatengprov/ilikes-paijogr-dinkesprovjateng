package go.id.dinkesjatengprov.ilikes.ui.activity.forgotPassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.AccountModel
import go.id.dinkesjatengprov.ilikes.data.model.AuthResetModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class ForgotPasswordViewModel(application: Application, repository: MainRepository) :
    BaseViewModel(application, repository) {

    val checkPassword = MutableLiveData<StatusRequestModel<Boolean?>>()
    val resetPassword = MutableLiveData<StatusRequestModel<Boolean?>>()

    fun checkPassword(phone: String) {
        checkPassword.value = StatusRequestModel.loading();
        requestObservable(
            repository.checkPassword(phone),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    checkPassword.value = StatusRequestModel.success(true)
                }

                override fun failure(failureModel: FailureModel) {
                    checkPassword.postValue(StatusRequestModel.error(failureModel))
                }
            })
    }

    fun resetPassword(phone: String, password: String, rePassword: String) {
        resetPassword.value = StatusRequestModel.loading();
        requestObservable(
            repository.resetPassword(AuthResetModel(phone, password, rePassword)),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    resetPassword.value = StatusRequestModel.success(true)
                }

                override fun failure(failureModel: FailureModel) {
                    resetPassword.postValue(StatusRequestModel.error(failureModel))
                }
            })
    }

}