package go.id.dinkesjatengprov.ilikes.ui.activity.peli

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLogin2Model
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLoginModel
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliResultModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class PeliViewModel(application: Application, repository: MainRepository) :
    BaseViewModel(application, repository) {

    val corjat = MutableLiveData<StatusRequestModel<PeliLoginModel>>()
    val peli = MutableLiveData<StatusRequestModel<PeliLogin2Model>>()
    val scan = MutableLiveData<StatusRequestModel<PeliResultModel>>()

    fun startToLoginPeli(nik: String?, name: String?){
        loginCorjat(nik, name)
    }

    private fun loginCorjat(nik: String?, name: String?) {
        corjat.postValue(StatusRequestModel.loading())
        request(repository.loginCorjat(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                val model = data as PeliLoginModel
                repository.getSharedPrefManager().tokenCorjat = model.accessToken
                loginPeli(nik, name)
            }

            override fun failure(failureModel: FailureModel) {
                corjat.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    private fun loginPeli(nik: String?, name: String?) {
        requestObservable(repository.loginPeli(nik, name), object : RetrofitListener {
            override fun <T> success(data: T?) {
                val model = data as PeliLogin2Model
                repository.getSharedPrefManager().tokenPeli = model.authToken
                repository.getSharedPrefManager().namePeli = name.toString().toUpperCase()
                repository.getSharedPrefManager().nikPeli = nik
                repository.getSharedPrefManager().isLoggedInPeli = true
                peli.postValue(StatusRequestModel.success(model))
            }

            override fun failure(failureModel: FailureModel) {
                peli.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun scanPeli(qrCode: String?) {
        scan.postValue(StatusRequestModel.loading())
        requestObservable(repository.scanPeli(qrCode), object : RetrofitListener {
            override fun <T> success(data: T?) {
                scan.postValue(StatusRequestModel.success(data as PeliResultModel))
            }

            override fun failure(failureModel: FailureModel) {
                scan.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }
}