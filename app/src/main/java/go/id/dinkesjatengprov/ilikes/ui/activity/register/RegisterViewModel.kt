package go.id.dinkesjatengprov.ilikes.ui.activity.register

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.AccountModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.helper.parser.isLowerContains
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class RegisterViewModel(application: Application, repository: MainRepository) :
    BaseViewModel(application, repository) {

    val register = MutableLiveData<StatusRequestModel<AccountModel>>()

    fun register(nik: String?, phone: String?, name: String?, password: String?) {
        register.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.authRegister(nik, phone, name, password),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    val accountModel = data as AccountModel

                    repository.getSharedPrefManager().isLoggedIn = true
                    repository.getSharedPrefManager().token = accountModel.token

                    register.postValue(StatusRequestModel.success(accountModel))
                }

                override fun failure(failureModel: FailureModel) {
                    val model = validationError(failureModel)
                    register.postValue(StatusRequestModel.error(model))
                }

            })
    }

    /**
     * Menerjemahkan error dari pesan error yang diberikan server
     * @param failureModel pesan error yang diberikan server
     * */
    fun validationError(failureModel: FailureModel): FailureModel {
        var failureMessage = failureModel
        if (failureModel.msgSystem.isLowerContains("unique")) {
            if (failureModel.msgSystem.isLowerContains("nik")) failureMessage =
                failureModel.copy(msgShow = "NIK telah digunakan")
            if (failureModel.msgSystem.isLowerContains("phone number")) failureMessage =
                failureModel.copy(msgShow = "No. HP telah digunakan")
        }
        return failureMessage
    }

}