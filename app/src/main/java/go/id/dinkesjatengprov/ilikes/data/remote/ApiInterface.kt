package go.id.dinkesjatengprov.ilikes.data.remote

import go.id.dinkesjatengprov.ilikes.data.model.*
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLogin2Model
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliLoginModel
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliResultModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodDonorModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiBloodStockModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiContactModel
import go.id.dinkesjatengprov.ilikes.data.model.pmi.PmiResponseModel
import go.id.dinkesjatengprov.ilikes.data.model.post.PostSTRTTK
import go.id.dinkesjatengprov.ilikes.data.model.psc.*
import go.id.dinkesjatengprov.ilikes.data.remote.model.ResponseModel
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {

    /********************** A U T H ************************/

    @POST("v1/auth/register")
    fun authRegister(
        @Body accountModel: AccountModel
    ): Observable<ResponseModel<AccountModel>>

    @PUT("v1/auth/login")
    fun authLogin(
        @Body loginModel: LoginModel
    ): Observable<ResponseModel<AccountModel>>

    @GET("v1/user/me")
    fun getUserMe(): Observable<ResponseModel<AccountModel>>

    @GET("v1/auth/check-phone/{phone}")
    fun checkPhone(
        @Path("phone") phone: String
    ): Observable<ResponseModel<Boolean?>>

    @POST("v1/auth/forgot-password")
    fun resetPassword(
        @Body body: AuthResetModel
    ): Observable<ResponseModel<Boolean?>>

    /********************** A R E A ************************/

    @GET("area/province")
    fun getProvince(): Observable<ResponseModel<ArrayList<SelectionModel>>>

    @GET("area/city")
    fun getCity(
        @Query("province") province: Long?
    ): Observable<ResponseModel<ArrayList<SelectionModel>>>

    @GET("area/district")
    fun getDistrict(
        @Query("city") city: Long?
    ): Observable<ResponseModel<ArrayList<SelectionModel>>>

    @GET("area/village")
    fun getSubdistrict(
        @Query("district") district: Long?
    ): Observable<ResponseModel<ArrayList<SelectionModel>>>

    /********************** L I S T ************************/

    @GET("v1/management/banner")
    fun getBanner(): Observable<ResponseModel<ArrayList<BannerModel>>>

    @GET("list/job")
    fun getJob(): Observable<ResponseModel<ArrayList<SelectionModel>>>

    @GET("list/faskes")
    fun getFaskes(): Observable<ResponseModel<ArrayList<FaskesModel>>>

    @GET("list/clinic")
    fun getClinic(
        @Query("faskes_id") id: Long?
    ): Observable<ResponseModel<ArrayList<ClinicModel>>>

    /********************** S T R T T K ************************/

    @GET("v1/license/strttk")
    fun getActiveSTRTTK(): Observable<ResponseModel<LicenseModel?>>

    @POST("v1/license/strttk")
    fun postSTRTTK(
        @Body strttk: PostSTRTTK
    ): Observable<ResponseModel<LicenseModel?>>

    @PUT("v1/license/strttk")
    fun putSTRTTK(
        @Body licenseModel: LicenseModel?
    ): Observable<ResponseModel<LicenseModel?>>

    @GET("v1/license/strttk/document")
    fun getDocumentSTRTTK(
        @Query("type") type: String?
    ): Observable<ResponseModel<ArrayList<LicenseRequirementModel>>>

    @GET("v1/license/strttk/document/upload")
    fun getUploadedSTRTTK(
        @Query("license_id") licenseId: String?
    ): Observable<ResponseModel<ArrayList<LicenseDocumentModel>>>

    @GET("v1/license/strttk/history-result")
    fun getHistorySTRTTK(): Observable<ResponseModel<ArrayList<LicenseModel>?>>

    @GET("v1/license/strttk/result")
    fun getResultSTRTTK(
        @Query("license_id") licenseId: String?
    ): Observable<ResponseModel<ArrayList<LicenseResultModel>?>>

    @Multipart
    @POST("v1/license/strttk/document/upload")
    fun postDocumentSTRTTK(
        @Part("license_id") licenseId: RequestBody?,
        @Part("document_id") documentId: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Observable<ResponseModel<LicenseDocumentModel>>

    @Multipart
    @PUT("v1/license/strttk/document/upload")
    fun putDocumentSTRTTK(
        @Part("id") id: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Observable<ResponseModel<LicenseDocumentModel>>

    @Streaming
    @GET
    fun getFile(
        @Url url: String?
    ): Observable<ResponseBody>

    @GET("kontakuddnew.php")
    fun getPmiContact(): Observable<PmiResponseModel<ArrayList<PmiContactModel>>>

    @GET("stokdarah.php")
    fun getPmiBloodStock(): Observable<PmiResponseModel<ArrayList<PmiBloodStockModel>>>

    @GET("jadwaldonornew.php")
    fun getPmiBloodDonor(): Observable<PmiResponseModel<ArrayList<PmiBloodDonorModel>>>

    /********************** T U B E L ************************/

    @GET("license/tubel")
    fun getActiveTubel(): Observable<ResponseModel<LicenseModel?>>

    @FormUrlEncoded
    @POST("license/tubel")
    fun postTubel(
        @Field("document_type") type: String?,
        @Field("nik") nik: String?,
        @Field("nip") nip: String?,
        @Field("name") name: String?,
        @Field("sex") sex: String?,
        @Field("place_birth") birthPlace: String?,
        @Field("birth_date") birthDate: String?
    ): Observable<ResponseModel<LicenseModel?>>

    @PUT("license/tubel")
    fun putTubel(
        @Body licenseModel: LicenseModel?
    ): Observable<ResponseModel<LicenseModel?>>

    @GET("license/tubel/document")
    fun getDocumentTubel(
        @Query("type") type: String?
    ): Observable<ResponseModel<ArrayList<LicenseRequirementModel>>>

    @GET("license/tubel/document/upload")
    fun getUploadedTubel(
        @Query("license_id") licenseId: String?
    ): Observable<ResponseModel<ArrayList<LicenseDocumentModel>>>

    @GET("license/tubel/result")
    fun getResultTubel(
        @Query("document_id") documentId: String?
    ): Observable<ResponseModel<LicenseResultModel>>

    @GET("license/tubel/history")
    fun getHistoryTubel(
        @Query("license_id") documentId: String?
    ): Observable<ResponseModel<ArrayList<LicenseModel>?>>

    @Multipart
    @POST("license/tubel/document/upload")
    fun postDocumentTubel(
        @Part("license_id") licenseId: RequestBody?,
        @Part("document_id") documentId: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Observable<ResponseModel<LicenseDocumentModel>>

    @Multipart
    @PUT("license/tubel/document/upload")
    fun putDocumentTubel(
        @Part("id") id: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Observable<ResponseModel<LicenseDocumentModel>>

    /********************** P E D U L I L I N D U N G I ************************/

    @FormUrlEncoded
    @POST("api/login")
    fun loginCorjat(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Observable<PeliLoginModel>

    @FormUrlEncoded
    @POST("api/peduli-lindungi/scan/login")
    fun loginPeli(
        @Field("nik") nik: String?,
        @Field("name") name: String?,
    ): Observable<ResponseModel<PeliLogin2Model>>

    @FormUrlEncoded
    @POST("api/peduli-lindungi/scan")
    fun scanPeli(
        @Field("token") token: String?,
        @Field("qrcode") qrcode: String?
    ): Observable<ResponseModel<PeliResultModel>>

    /********************** P S C 1 1 9 ************************/

    @POST("login")
    fun loginPSC(
        @Body body: PostAccountPscModel
    ): Observable<AccountPscModel>

    @Multipart
    @POST("image")
    fun uploadImage(@Part file: MultipartBody.Part): Flowable<UploadPhotoModel>

    @POST("digisar-go/reports")
    fun postReport(
        @Body body: PostReportBodyModel
    ): Observable<PscReportResultModel>

}