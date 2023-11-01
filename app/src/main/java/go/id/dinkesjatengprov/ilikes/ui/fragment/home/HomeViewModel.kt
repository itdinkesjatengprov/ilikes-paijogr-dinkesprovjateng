package go.id.dinkesjatengprov.ilikes.ui.fragment.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.BannerModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class HomeViewModel (application: Application, repository: MainRepository): BaseViewModel(application, repository){

    val banner = MutableLiveData<StatusRequestModel<ArrayList<BannerModel>>>()

    fun getBanner(){
        requestObservable(repository.getBanner(), object : RetrofitListener{
            override fun <T> success(data: T?) {
                banner.postValue(StatusRequestModel.success(data as ArrayList<BannerModel>))
            }

            override fun failure(failureModel: FailureModel) {
                banner.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

}