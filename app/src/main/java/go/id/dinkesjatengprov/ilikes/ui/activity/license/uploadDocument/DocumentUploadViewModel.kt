package go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.remote.RetrofitConfig
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel
import okhttp3.ResponseBody
import java.io.File

class DocumentUploadViewModel(app: Application, repository: MainRepository) :
    BaseViewModel(app, repository) {

    val uploadDocumentSTRTTK = MutableLiveData<StatusRequestModel<LicenseDocumentModel>>()
    val uploadDocumentTubel = MutableLiveData<StatusRequestModel<LicenseDocumentModel>>()
    val pdf = MutableLiveData<StatusRequestModel<ResponseBody>>()

    fun postDocumentSTRTTK(
        documentType: String?,
        licenseId: String?,
        documentId: String?,
        file: File?
    ) {
        uploadDocumentSTRTTK.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.postDocumentStrttk(documentType, licenseId, documentId, file),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    uploadDocumentSTRTTK.postValue(StatusRequestModel.success(data as LicenseDocumentModel))
                }

                override fun failure(failureModel: FailureModel) {
                    uploadDocumentSTRTTK.postValue(StatusRequestModel.error(failureModel))
                }
            })
    }

    fun downloadFile(filename: String?) {
        val url = RetrofitConfig.BASE_URL + "v1/download/" + filename
        pdf.postValue(StatusRequestModel.loading())
        request(repository.downloadPdf(url), object : RetrofitListener {
            override fun <T> success(data: T?) {
                pdf.postValue(StatusRequestModel.success(data as ResponseBody))
            }

            override fun failure(failureModel: FailureModel) {
                pdf.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun updateDocumentSTRTTK(documentType: String?, id: String?, file: File?) {
        uploadDocumentSTRTTK.postValue(StatusRequestModel.loading())
        requestObservable(repository.putDocumentStrttk(documentType, id, file), object : RetrofitListener {
            override fun <T> success(data: T?) {
                uploadDocumentSTRTTK.postValue(StatusRequestModel.success(data as LicenseDocumentModel))
            }

            override fun failure(failureModel: FailureModel) {
                uploadDocumentSTRTTK.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun postDocumentTubel(licenseId: String?, documentId: String?, file: File?) {
        uploadDocumentTubel.postValue(StatusRequestModel.loading())
        //todo API masih error
//        requestObservable(
//            repository.postDocumentTubel(licenseId, documentId, file),
//            object : RetrofitListener {
//                override fun <T> success(data: T?) {
//                    uploadDocument.postValue(StatusRequestModel.success(data as LicenseDocumentModel))
//                }
//
//                override fun failure(failureModel: FailureModel) {
//                    uploadDocument.postValue(StatusRequestModel.error(failureModel))
//                }
//            })
        uploadDocumentTubel.postValue(StatusRequestModel.success(LicenseDocumentModel()))
    }

    fun updateDocumentTubel(id: String?, file: File?) {
        uploadDocumentTubel.postValue(StatusRequestModel.loading())
//        requestObservable(repository.putDocumentTubel(id, file), object : RetrofitListener {
//            override fun <T> success(data: T?) {
//                uploadDocument.postValue(StatusRequestModel.success(data as LicenseDocumentModel))
//            }
//
//            override fun failure(failureModel: FailureModel) {
//                uploadDocument.postValue(StatusRequestModel.error(failureModel))
//            }
//        })
        uploadDocumentTubel.postValue(StatusRequestModel.success(LicenseDocumentModel()))
    }
}