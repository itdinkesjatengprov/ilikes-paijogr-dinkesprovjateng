package go.id.dinkesjatengprov.ilikes.ui.activity.pmi

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodDonorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodStockModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiContactModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel
import java.util.ArrayList

class PmiViewModel(app: Application, repository: MainRepository) : BaseViewModel(app, repository) {

    val contact = MutableLiveData<StatusRequestModel<ArrayList<PmiContactModel>>>()
    val stock = MutableLiveData<StatusRequestModel<ArrayList<PmiBloodStockModel>>>()
    val donor = MutableLiveData<StatusRequestModel<ArrayList<PmiBloodDonorModel>>>()

    fun getContact() {
        contact.postValue(StatusRequestModel.loading())
        pmiRequestObservable(repository.getPmiContact(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                contact.postValue(StatusRequestModel.success(data as ArrayList<PmiContactModel>))
            }

            override fun failure(failureModel: FailureModel) {
                contact.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun getStock() {
        stock.postValue(StatusRequestModel.loading())
        pmiRequestObservable(repository.getPmiBloodStock(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                stock.postValue(StatusRequestModel.success(data as ArrayList<PmiBloodStockModel>))
            }

            override fun failure(failureModel: FailureModel) {
                stock.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun getDonor() {
        donor.postValue(StatusRequestModel.loading())
        pmiRequestObservable(repository.getPmiBloodDonor(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                donor.postValue(StatusRequestModel.success(data as ArrayList<PmiBloodDonorModel>))
            }

            override fun failure(failureModel: FailureModel) {
                donor.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

}