package go.id.dinkesjatengprov.ilikes.data

import android.content.Context
import android.util.Log
import go.id.dinkesjatengprov.ilikes.data.local.room.RoomConfig
import go.id.dinkesjatengprov.ilikes.data.local.room.RoomListener
import go.id.dinkesjatengprov.ilikes.data.local.sharedpreference.SharedPrefManager
import go.id.dinkesjatengprov.ilikes.data.model.*
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLogin2Model
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLoginModel
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliResultModel
import go.id.dinkesjatengprov.ilikes.data.model.post.PostSTRTTK
import go.id.dinkesjatengprov.ilikes.data.model.psc.*
import go.id.dinkesjatengprov.ilikes.data.remote.PeliConfig
import go.id.dinkesjatengprov.ilikes.data.remote.PmiConfig
import go.id.dinkesjatengprov.ilikes.data.remote.PscConfig
import go.id.dinkesjatengprov.ilikes.data.remote.RetrofitConfig
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.ResponseModel
import go.id.dinkesjatengprov.ilikes.helper.TAG_ROOM_REQUEST_FAILED
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

class MainRepository(val context: Context) {

    //API SERVICE PAIJO
    var apiService = RetrofitConfig.setApiService()
    var apiServiceContext = RetrofitConfig.setApiService(context)

    //API SERVICE PEDULI LINDUNGI
    var apiServicePeli = PeliConfig.setApiService()
    var apiServiceContextPeli = PeliConfig.setApiService(context)

    //API SERVICE PSC
    var apiServicePsc = PscConfig.setApiService()
    var apiServiceContextPsc = PscConfig.setApiService(context)

    //API SERVICE PMI
    var apiServicePmi = PmiConfig.setApiService()

    //ROOM
    val dao = RoomConfig.getInstance(context).dao

    fun getSharedPrefManager(): SharedPrefManager {
        return SharedPrefManager(context)
    }

    fun logout() {
        getSharedPrefManager().token = null
        getSharedPrefManager().isLoggedIn = false

        logoutPSC()
        logoutPeli()
    }

    fun logoutPSC() {
        getSharedPrefManager().logoutPSC()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dao.clearAccountTable()
            } catch (e: Exception) {
                Log.d(TAG_ROOM_REQUEST_FAILED, "logoutPSC: Gagal menghapus akun PSC")
            }
        }
    }

    fun logoutPeli() {
        getSharedPrefManager().tokenCorjat = null
        getSharedPrefManager().tokenPeli = null
        getSharedPrefManager().isLoggedInPeli = false
        getSharedPrefManager().namePeli = null
        getSharedPrefManager().nikPeli = null
    }

    /********************** A U T H ************************/

    fun authRegister(
        nik: String?,
        phone: String?,
        name: String?,
        password: String?
    ): Observable<ResponseModel<AccountModel>> {
        val accountModel = AccountModel(
            nik = nik,
            phone = phone,
            name = name,
            password = password
        )
        return apiService.authRegister(accountModel)
    }

    fun authLogin(
        phone: String,
        password: String
    ): Observable<ResponseModel<AccountModel>> {
        val model = LoginModel(phone, password)
        return apiService.authLogin(model)
    }

    fun getUserMe(): Observable<ResponseModel<AccountModel>> {
        return apiServiceContext.getUserMe()
    }

    fun checkPassword(phone: String): Observable<ResponseModel<Boolean?>> {
        return apiServiceContext.checkPhone(phone)
    }

    fun resetPassword(body: AuthResetModel): Observable<ResponseModel<Boolean?>> {
        return apiServiceContext.resetPassword(body)
    }

    /********************** A R E A ************************/

    fun getProvince(): Observable<ResponseModel<ArrayList<SelectionModel>>> {
        return apiService.getProvince()
    }

    fun getCity(province: Long?): Observable<ResponseModel<ArrayList<SelectionModel>>> {
        return apiService.getCity(province)
    }

    fun getDistrict(city: Long?): Observable<ResponseModel<ArrayList<SelectionModel>>> {
        return apiService.getDistrict(city)
    }

    fun getSubdistrict(district: Long?): Observable<ResponseModel<ArrayList<SelectionModel>>> {
        return apiService.getSubdistrict(district)
    }

    /********************** L I S T ************************/

    fun getBanner(): Observable<ResponseModel<ArrayList<BannerModel>>> {
        return apiServiceContext.getBanner()
    }

    fun getJob(): Observable<ResponseModel<ArrayList<SelectionModel>>> {
        return apiServiceContext.getJob()
    }

    fun getFaskes(): Observable<ResponseModel<ArrayList<FaskesModel>>> {
        return apiServiceContext.getFaskes()
    }

    fun getClinic(faskesId: Long?): Observable<ResponseModel<ArrayList<ClinicModel>>> {
        return apiServiceContext.getClinic(faskesId)
    }

    /********************** S T R T T K ************************/

    fun getActiveSTRTTK(): Observable<ResponseModel<LicenseModel?>> {
        return apiServiceContext.getActiveSTRTTK()
    }

    fun postStrttk(
        type: String?,
        nik: String?,
        name: String?,
        sex: String?,
        placeBirth: String?,
        date: String?,
        lastEducation: String?,
        schoolName: String?,
        graduationYear: String?
    ): Observable<ResponseModel<LicenseModel?>> {
        val strttk = PostSTRTTK(
            nik = nik,
            documentType = type,
            graduationYear = graduationYear,
            birthDate = date,
            sex = sex,
            name = name,
            placeBirth = placeBirth,
            schoolName = schoolName,
            schoolType = lastEducation
        )
        return apiServiceContext.postSTRTTK(strttk)
    }

    fun updateStrttk(
        license: LicenseModel?
    ): Observable<ResponseModel<LicenseModel?>> {
        return apiServiceContext.putSTRTTK(license)
    }

    fun getRequirementStrttk(type: String?): Observable<ResponseModel<ArrayList<LicenseRequirementModel>>> {
        return apiServiceContext.getDocumentSTRTTK(type)
    }

    fun getDocumentStrttk(licenseId: String?): Observable<ResponseModel<ArrayList<LicenseDocumentModel>>> {
        return apiServiceContext.getUploadedSTRTTK(licenseId)
    }

    fun getHistorySTRTTK(): Observable<ResponseModel<ArrayList<LicenseModel>?>> {
        return apiServiceContext.getHistorySTRTTK()
    }

    fun getResultSTRTTK(licenseId: String?): Observable<ResponseModel<ArrayList<LicenseResultModel>?>> {
        return apiServiceContext.getResultSTRTTK(licenseId)
    }

    fun postDocumentStrttk(
        documentType: String?,
        licenseId: String?,
        documentId: String?,
        file: File?
    ): Observable<ResponseModel<LicenseDocumentModel>> {
        val partLicenseId = RequestBody.create("text/plain".toMediaTypeOrNull(), licenseId ?: "")
        val partDocumentId = RequestBody.create("text/plain".toMediaTypeOrNull(), documentId ?: "")

        val proof = RequestBody.create("$documentType".toMediaTypeOrNull(), file!!)
        val partProof = MultipartBody.Part.createFormData("file", file.name, proof)

        Log.d("D4TA", "postDocumentStrttk: $licenseId $documentId")

        return apiServiceContext.postDocumentSTRTTK(partLicenseId, partDocumentId, partProof)
    }

    fun putDocumentStrttk(
        documentType: String?,
        id: String?,
        file: File?
    ): Observable<ResponseModel<LicenseDocumentModel>> {
        Log.d("1111D", "putDocumentStrttk: $id")
        val partId = RequestBody.create("text/plain".toMediaTypeOrNull(), id ?: "")
        val partStatus = RequestBody.create("text/plain".toMediaTypeOrNull(), "")

        val proof = RequestBody.create("$documentType".toMediaTypeOrNull(), file!!)
        val partProof = MultipartBody.Part.createFormData("file", file.name, proof)

        return apiServiceContext.putDocumentSTRTTK(partId, partStatus, partProof)
    }

    fun downloadPdf(url: String?): Observable<ResponseBody> {
        return apiServiceContext.getFile(url)
    }

    /********************** T U B E L ************************/

    fun getActiveTubel(): Observable<ResponseModel<LicenseModel?>> {
        return apiServiceContext.getActiveTubel()
    }

    fun postTubel(
        type: String?,
        nik: String?,
        nip: String?,
        name: String?,
        sex: String?,
        placeBirth: String?,
        date: String?
    ): Observable<ResponseModel<LicenseModel?>> {
        return apiServiceContext.postTubel(type, nik, nip, name, sex, placeBirth, date)
    }

    fun updateTubel(
        license: LicenseModel?
    ): Observable<ResponseModel<LicenseModel?>> {
        return apiServiceContext.putTubel(license)
    }

    fun getRequirementTubel(type: String?): Observable<ResponseModel<ArrayList<LicenseRequirementModel>>> {
        return apiServiceContext.getDocumentTubel(type)
    }

    fun getDocumentTubel(licenseId: String?): Observable<ResponseModel<ArrayList<LicenseDocumentModel>>> {
        return apiServiceContext.getUploadedTubel(licenseId)
    }

    fun postDocumentTubel(
        licenseId: String?,
        documentId: String?,
        file: File?
    ): Observable<ResponseModel<LicenseDocumentModel>> {
        val partLicenseId = RequestBody.create("text/plain".toMediaTypeOrNull(), licenseId ?: "")
        val partDocumentId = RequestBody.create("text/plain".toMediaTypeOrNull(), documentId ?: "")

        val proof = RequestBody.create("image/png".toMediaTypeOrNull(), file!!)
        val partProof = MultipartBody.Part.createFormData("file", file.name, proof)

        return apiServiceContext.postDocumentTubel(partLicenseId, partDocumentId, partProof)
    }

    fun putDocumentTubel(
        id: String?,
        file: File?
    ): Observable<ResponseModel<LicenseDocumentModel>> {
        val partId = RequestBody.create("text/plain".toMediaTypeOrNull(), id ?: "")

        val proof = RequestBody.create("image/png".toMediaTypeOrNull(), file!!)
        val partProof = MultipartBody.Part.createFormData("file", file.name, proof)

        return apiServiceContext.putDocumentTubel(partId, partProof)
    }

    /********************** P E D U L I L I N D U N G I ************************/

    fun loginCorjat(): Observable<PeliLoginModel> {
        return apiServicePeli.loginCorjat("apiabsensigrms", "coronaminggat")
    }

    fun loginPeli(nik: String?, name: String?): Observable<ResponseModel<PeliLogin2Model>> {
        apiServiceContextPeli = PeliConfig.setApiService(context)
        return apiServiceContextPeli.loginPeli(nik, name)
    }

    fun scanPeli(qrcode: String?): Observable<ResponseModel<PeliResultModel>> {
        val tokenPeli = getSharedPrefManager().tokenPeli
        return apiServiceContextPeli.scanPeli(tokenPeli, qrcode)
    }

    /********************** P S C 1 1 9 ************************/

    fun loginPSC(account: PostAccountPscModel): Observable<AccountPscModel> {
        return apiServicePsc.loginPSC(account)
    }

    fun saveAccountPSC(account: AccountPscModel?, listener: RoomListener) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dao.insertAccount(account)
                listener.onSuccess(account)
            } catch (e: Exception) {
                listener.onFailed(
                    FailureModel(
                        msgShow = "Gagal menyimpan data akun. " + e.message,
                        msgSystem = e.message
                    )
                )
            }
        }
    }

    fun getAccountPSC(listener: RoomListener) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val account = dao.getOneUser()
                listener.onSuccess(account)
            } catch (e: Exception) {
                listener.onFailed(
                    FailureModel(
                        msgShow = "Gagal mendapatkan data akun. " + e.message,
                        msgSystem = e.message
                    )
                )
            }
        }
    }

    fun uploadPhoto(imageRequest: PscPhotoModel): Flowable<String> {
        val img = RequestBody.create("image/png".toMediaTypeOrNull(), imageRequest.fileImage!!)
        val partImage = MultipartBody.Part.createFormData("file", imageRequest.filename, img)
        return apiServiceContextPsc.uploadImage(partImage).map {
            it.data.url
        }
    }

    fun uploadPhotos(photos: ArrayList<PscPhotoModel>): Flowable<List<String>> {
        val imageUrls = mutableListOf<String>()

        return Flowable.fromIterable(photos).flatMap {
            uploadPhoto(it)
        }.flatMap {
            imageUrls.add(it)
            Flowable.just(imageUrls.toList())
        }
    }

    fun uploadReport(body: PostReportBodyModel): Observable<PscReportResultModel> {
        return apiServiceContextPsc.postReport(body)
    }

    /********************** P M I ************************/

    fun getPmiContact() = apiServicePmi.getPmiContact()
    fun getPmiBloodStock() = apiServicePmi.getPmiBloodStock()
    fun getPmiBloodDonor() = apiServicePmi.getPmiBloodDonor()

}