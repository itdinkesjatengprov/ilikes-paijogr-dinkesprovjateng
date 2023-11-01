package go.id.dinkesjatengprov.ilikes.ui.activity.psc

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.local.room.RoomListener
import go.id.dinkesjatengprov.ilikes.data.model.psc.*
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener2
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class PscViewModel(app: Application, repository: MainRepository) : BaseViewModel(app, repository) {

    val account = MutableLiveData<StatusRequestModel<AccountPscModel>>()
    val report = MutableLiveData<StatusRequestModel<PscReportResultModel>>()

    fun login(username: String?, password: String?) {
        account.postValue(StatusRequestModel.loading())
        request(
            repository.loginPSC(PostAccountPscModel(username, password)),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    saveAccount(data as AccountPscModel)
                }

                override fun failure(failureModel: FailureModel) {
                    account.postValue(StatusRequestModel.error(failureModel))
                }
            })
    }

    fun saveAccount(body: AccountPscModel?) {
        repository.saveAccountPSC(body, object : RoomListener {
            override fun <T> onSuccess(result: T) {
                repository.getSharedPrefManager().loginPSC(body?.token)
                account.postValue(StatusRequestModel.success(result as AccountPscModel))
            }

            override fun onFailed(failureModel: FailureModel) {
                account.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun postNewReport(photos: ArrayList<PscPhotoModel>, body: PostReportBodyModel){
        report.postValue(StatusRequestModel.loading())

        requestFlowable(repository.uploadPhotos(photos), object : RetrofitListener2{
            override fun <T> success(data: T?) {

            }

            override fun <T> next(data: T) {
                Log.d("FL0W4BL3", "next: $data")
                postBody(body, data as List<String>)
            }

            override fun failure(failureModel: FailureModel) {
                report.postValue(StatusRequestModel.error(failureModel))
            }
        })

    }

    private fun postBody(body: PostReportBodyModel, list: List<String>) {
        repository.getAccountPSC(object : RoomListener{
            override fun <T> onSuccess(result: T) {
                body.reportedId = (result as AccountPscModel).id
                body.reportImage = list

                request(repository.uploadReport(body), object : RetrofitListener{
                    override fun <T> success(data: T?) {
                        report.postValue(StatusRequestModel.success(data as PscReportResultModel))
                    }

                    override fun failure(failureModel: FailureModel) {
                        report.postValue(StatusRequestModel.error(failureModel))
                    }

                })
            }

            override fun onFailed(failureModel: FailureModel) {
                report.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

}