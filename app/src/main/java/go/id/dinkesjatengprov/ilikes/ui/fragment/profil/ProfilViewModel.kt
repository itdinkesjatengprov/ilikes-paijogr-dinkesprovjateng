package go.id.dinkesjatengprov.ilikes.ui.fragment.profil

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.AccountModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class ProfilViewModel (application: Application, repository: MainRepository): BaseViewModel(application, repository){

    val auth = MutableLiveData<StatusRequestModel<AccountModel>>()

    fun getAuth(){
        auth.postValue(StatusRequestModel.loading())
        requestObservable(repository.getUserMe(), object : RetrofitListener{
            override fun <T> success(data: T?) {
                val account = data as AccountModel
                auth.postValue(StatusRequestModel.success(account))
            }

            override fun failure(failureModel: FailureModel) {
                auth.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

}