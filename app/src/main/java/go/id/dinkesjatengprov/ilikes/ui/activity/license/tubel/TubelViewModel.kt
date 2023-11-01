package go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseRequirementModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel
import java.io.File

class TubelViewModel(app: Application, repository: MainRepository) :
    BaseViewModel(app, repository) {

    val tubelNew = MutableLiveData<StatusRequestModel<LicenseModel?>>()
    val requirement = MutableLiveData<StatusRequestModel<ArrayList<LicenseDocumentModel>>>()
    val document = MutableLiveData<StatusRequestModel<ArrayList<LicenseDocumentModel>>>()
    val uploadDocument = MutableLiveData<StatusRequestModel<LicenseDocumentModel>>()
    val tubelActive = MutableLiveData<StatusRequestModel<LicenseModel?>>()

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

    fun postTubel(
        type: SelectionModel?,
        nik: String?,
        nip: String?,
        name: String?,
        sex: String?,
        birthPlace: String?,
        date: String?
    ) {
        tubelNew.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.postTubel(type?.id, nik, nip, name, sex, birthPlace, date),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    tubelNew.postValue(StatusRequestModel.success(data as LicenseModel))
                }

                override fun failure(failureModel: FailureModel) {
                    tubelNew.postValue(StatusRequestModel.error(failureModel))
                }

            })
    }

    fun updateTubel(
        license: LicenseModel?
    ) {
        tubelNew.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.updateTubel(license),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    tubelNew.postValue(StatusRequestModel.success(data as LicenseModel))
                }

                override fun failure(failureModel: FailureModel) {
                    tubelNew.postValue(StatusRequestModel.error(failureModel))
                }

            })
    }

    fun getRequirement(type: String?, dummy: ArrayList<LicenseRequirementModel>) {
        requirement.postValue(StatusRequestModel.loading())
        requestObservable(repository.getRequirementTubel(type), object : RetrofitListener {
            override fun <T> success(data: T?) {
//                var list = data as ArrayList<LicenseRequirementModel>
                var list = dummy
                list = list.filter { it.status == "ACTIVE" } as ArrayList<LicenseRequirementModel>
                if (list.isNullOrEmpty()) {
                    requirement.postValue(StatusRequestModel.empty())
                } else {
                    val listDocument = arrayListOf<LicenseDocumentModel>()
                    for (i in list) {
                        listDocument.add(
                            LicenseDocumentModel(document = i)
                        )
                    }
                    requirement.postValue(StatusRequestModel.success(listDocument))
                }
            }

            override fun failure(failureModel: FailureModel) {
                requirement.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getDocument(licenseId: String?) {
        document.postValue(StatusRequestModel.loading())
        requestObservable(repository.getDocumentTubel(licenseId), object : RetrofitListener {
            override fun <T> success(data: T?) {
                var list = data as ArrayList<LicenseDocumentModel>
                list = populateRequirementWithDocument(list)
                document.postValue(StatusRequestModel.success(list))
            }

            override fun failure(failureModel: FailureModel) {
                document.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    private fun populateRequirementWithDocument(documentList: ArrayList<LicenseDocumentModel>): ArrayList<LicenseDocumentModel> {
        val result = arrayListOf<LicenseDocumentModel>()
        val requirementList = requirement.value?.data
        if (requirementList != null) {
            for (i in requirementList) {
                val filterDocument = documentList.filter { it.document?.id == i.document?.id }
                if (filterDocument.isNotEmpty()) {
                    result.add(filterDocument[0])
                } else {
                    result.add(i)
                }
            }
            return result
        }
        return arrayListOf()
    }

    fun postFileDocument(licenseId: String?, documentId: String?, file: File?) {
        uploadDocument.postValue(StatusRequestModel.loading())
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
        uploadDocument.postValue(StatusRequestModel.success(LicenseDocumentModel()))
    }

    fun updateFileDocument(id: String?, file: File?) {
        uploadDocument.postValue(StatusRequestModel.loading())
//        requestObservable(repository.putDocumentTubel(id, file), object : RetrofitListener {
//            override fun <T> success(data: T?) {
//                uploadDocument.postValue(StatusRequestModel.success(data as LicenseDocumentModel))
//            }
//
//            override fun failure(failureModel: FailureModel) {
//                uploadDocument.postValue(StatusRequestModel.error(failureModel))
//            }
//        })
        uploadDocument.postValue(StatusRequestModel.success(LicenseDocumentModel()))
    }

}