package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.app.Application
import androidx.lifecycle.MutableLiveData
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.ClinicModel
import go.id.dinkesjatengprov.ilikes.data.model.FaskesModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.RetrofitListener
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequestModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class RegisterServiceViewModel(application: Application, repository: MainRepository) :
    BaseViewModel(application, repository) {

    val patient = MutableLiveData<StatusRequestModel<ArrayList<BookingModel>>>()
    val job = MutableLiveData<StatusRequestModel<ArrayList<SelectionModel>?>>()
    val faskes = MutableLiveData<StatusRequestModel<ArrayList<FaskesModel>?>>()
    val clinic = MutableLiveData<StatusRequestModel<ArrayList<ClinicModel>?>>()

    //REGION
    val province = MutableLiveData<StatusRequestModel<ArrayList<SelectionModel>?>>()
    val city = MutableLiveData<StatusRequestModel<ArrayList<SelectionModel>?>>()
    val district = MutableLiveData<StatusRequestModel<ArrayList<SelectionModel>?>>()
    val subdistrict = MutableLiveData<StatusRequestModel<ArrayList<SelectionModel>?>>()

    fun getPatient() {
        patient.postValue(StatusRequestModel.success(ArrayList()))
    }

    fun getJob() {
        job.postValue(StatusRequestModel.loading())
        requestObservable(repository.getJob(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                job.postValue(StatusRequestModel.success(data as ArrayList<SelectionModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                job.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getFaskes() {
        faskes.postValue(StatusRequestModel.loading())
        requestObservable(repository.getFaskes(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                faskes.postValue(StatusRequestModel.success(data as ArrayList<FaskesModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                faskes.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getClinic(faskesId: Long?) {
        clinic.postValue(StatusRequestModel.success(ArrayList()))
//        clinic.postValue(StatusRequestModel.loading())
//        requestObservable(repository.getClinic(faskesId), object : RetrofitListener {
//            override fun <T> success(data: T?) {
//                clinic.postValue(StatusRequestModel.success(data as ArrayList<ClinicModel>?))
//            }
//
//            override fun failure(failureModel: FailureModel) {
//                clinic.postValue(StatusRequestModel.error(failureModel))
//            }
//
//        })
    }

    /**
     * GET LIST REGION (PROVINCE, CITY, DISTRICT AND SUBDISTRICT)
     * */

    fun getProvince() {
        province.postValue(StatusRequestModel.loading())
        requestObservable(repository.getProvince(), object : RetrofitListener {
            override fun <T> success(data: T?) {
                province.postValue(StatusRequestModel.success(data as ArrayList<SelectionModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                province.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getCity(provinceId: Long?) {
        city.postValue(StatusRequestModel.loading())
        requestObservable(repository.getCity(provinceId), object : RetrofitListener {
            override fun <T> success(data: T?) {
                city.postValue(StatusRequestModel.success(data as ArrayList<SelectionModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                city.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getDistrict(cityId: Long?) {
        district.postValue(StatusRequestModel.loading())
        requestObservable(repository.getDistrict(cityId), object : RetrofitListener {
            override fun <T> success(data: T?) {
                district.postValue(StatusRequestModel.success(data as ArrayList<SelectionModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                district.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }

    fun getSubdistrict(districtId: Long?) {
        subdistrict.postValue(StatusRequestModel.loading())
        requestObservable(repository.getSubdistrict(districtId), object : RetrofitListener {
            override fun <T> success(data: T?) {
                subdistrict.postValue(StatusRequestModel.success(data as ArrayList<SelectionModel>?))
            }

            override fun failure(failureModel: FailureModel) {
                subdistrict.postValue(StatusRequestModel.error(failureModel))
            }

        })
    }


}