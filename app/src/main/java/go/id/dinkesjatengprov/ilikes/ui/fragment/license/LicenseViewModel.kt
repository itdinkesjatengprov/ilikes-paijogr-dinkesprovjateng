package go.id.dinkesjatengprov.ilikes.ui.fragment.license

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.helper.parser.isLowerContains
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class LicenseViewModel(app: Application, repository: MainRepository) :
    BaseViewModel(app, repository) {

    val strttkActive = MutableLiveData<StatusRequestModel<LicenseModel?>>()
    val tubelActive = MutableLiveData<StatusRequestModel<LicenseModel?>>()

    fun getActiveSTRTTK() {
        strttkActive.postValue(StatusRequestModel.loading())
        requestObservable(repository.getActiveSTRTTK(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                val result = data as LicenseModel?
                strttkActive.postValue(StatusRequestModel.success(result))
            }

            override fun failure(failureModel: FailureModel) {
                if (failureModel.msgSystem.isLowerContains("no rows in result set")){
                    strttkActive.postValue(StatusRequestModel.empty())
                }else{
                    strttkActive.postValue(StatusRequestModel.error(failureModel))
                }
            }

        })
    }

    fun getActiveTubel() {
        tubelActive.postValue(StatusRequestModel.loading())
        requestObservable(repository.getActiveTubel(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                val result = data as LicenseModel?
                tubelActive.postValue(StatusRequestModel.success(result))
            }

            override fun failure(failureModel: FailureModel) {
                tubelActive.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

}