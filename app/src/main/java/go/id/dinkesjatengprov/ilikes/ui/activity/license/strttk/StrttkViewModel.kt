package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.*
import go.id.dinkesjatengprov.ilikes.data.remote.RetrofitConfig
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel
import okhttp3.ResponseBody
import java.io.File

class StrttkViewModel(app: Application, repository: MainRepository) :
    BaseViewModel(app, repository) {

    val newStrttk = MutableLiveData<StatusRequestModel<LicenseModel?>>()
    val requirement = MutableLiveData<StatusRequestModel<ArrayList<LicenseDocumentModel>>>()
    val document = MutableLiveData<StatusRequestModel<ArrayList<LicenseDocumentModel>>>()
    val strttkActive = MutableLiveData<StatusRequestModel<LicenseModel?>>()
    val history = MutableLiveData<StatusRequestModel<ArrayList<LicenseModel>>>()
    val result = MutableLiveData<StatusRequestModel<ArrayList<LicenseResultModel>>>()
    val fileResult = MutableLiveData<StatusRequestModel<ResponseBody>>()
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

    fun getActiveSTRTTK() {
        strttkActive.postValue(StatusRequestModel.loading())
        requestObservable(repository.getActiveSTRTTK(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                val result = data as LicenseModel?
                strttkActive.postValue(StatusRequestModel.success(result))
            }

            override fun failure(failureModel: FailureModel) {
                strttkActive.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun postStrttk(
        type: SelectionModel?,
        nik: String?,
        name: String?,
        sex: String?,
        placeBirth: String?,
        date: String?,
        lastEducation: String?,
        schoolName: String?,
        graduationYear: String?
    ) {
        newStrttk.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.postStrttk(
                type?.id,
                nik,
                name,
                sex,
                placeBirth,
                date,
                lastEducation,
                schoolName,
                graduationYear
            ),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    newStrttk.postValue(StatusRequestModel.success(data as LicenseModel))
                }

                override fun failure(failureModel: FailureModel) {
                    newStrttk.postValue(StatusRequestModel.error(failureModel))
                }

            })
    }

    fun updateStrttk(
        license: LicenseModel?
    ) {
        newStrttk.postValue(StatusRequestModel.loading())
        requestObservable(
            repository.updateStrttk(license),
            object : RetrofitListener {
                override fun <T> success(data: T?) {
                    newStrttk.postValue(StatusRequestModel.success(data as LicenseModel))
                }

                override fun failure(failureModel: FailureModel) {
                    newStrttk.postValue(StatusRequestModel.error(failureModel))
                }

            })
    }

    fun getRequirement(type: String?) {
        requirement.postValue(StatusRequestModel.loading())
        requestObservable(repository.getRequirementStrttk(type), object : RetrofitListener {
            override fun <T> success(data: T?) {
                var list = data as ArrayList<LicenseRequirementModel>
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
        requestObservable(repository.getDocumentStrttk(licenseId), object : RetrofitListener {
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

    fun getHistory() {
        history.postValue(StatusRequestModel.loading())
        requestObservable(repository.getHistorySTRTTK(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                history.postValue(StatusRequestModel.success(data as ArrayList<LicenseModel>))
            }

            override fun failure(failureModel: FailureModel) {
                history.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun getResult(licenseId: String?) {
        result.postValue(StatusRequestModel.loading())
        requestObservable(repository.getResultSTRTTK(licenseId), object : RetrofitListener {
            override fun <T> success(data: T?) {
                result.postValue(StatusRequestModel.success(data as ArrayList<LicenseResultModel>))
            }

            override fun failure(failureModel: FailureModel) {
                result.postValue(StatusRequestModel.error(failureModel))
            }
        })
    }

    fun downloadFile(filename: String?) {
        val url = RetrofitConfig.BASE_URL + "v1/download/" + filename
        fileResult.postValue(StatusRequestModel.loading())
        request(repository.downloadPdf(url), object : RetrofitListener {
            override fun <T> success(data: T?) {
                fileResult.postValue(StatusRequestModel.success(data as ResponseBody))
            }

            override fun failure(failureModel: FailureModel) {
                fileResult.postValue(StatusRequestModel.error(failureModel))
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

}