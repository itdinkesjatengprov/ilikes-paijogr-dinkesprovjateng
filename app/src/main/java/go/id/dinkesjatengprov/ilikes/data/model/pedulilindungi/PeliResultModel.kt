package go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeliResultModel(

    @field:SerializedName("reason")
    val reason: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("vaccine")
    val vaccine: List<PeliVaccineItemModel?>? = null,

    @field:SerializedName("userStatus")
    val userStatus: String? = null,

    @field:SerializedName("checkOutTime")
    val checkOutTime: String? = null,

    @field:SerializedName("crowd")
    val crowd: Int? = null,

    @field:SerializedName("checkInTime")
    val checkInTime: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("place")
    val place: PlaceModel? = null,

    @field:SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class PeliQrcodePlaceModel(

    @field:SerializedName("checkIn")
    val checkIn: String? = null,

    @field:SerializedName("checkOut")
    val checkOut: String? = null
) : Parcelable

@Parcelize
data class PeliVaccineItemModel(

    @field:SerializedName("vaccinationDate")
    val vaccinationDate: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("vaccinated")
    val vaccinated: PeliVaccineModel? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class Image(

    @field:SerializedName("fileUrl")
    val fileUrl: String? = null
) : Parcelable

@Parcelize
data class CheckoutPin(

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("radius")
    val radius: Int? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null
) : Parcelable

@Parcelize
data class PlaceModel(

    @field:SerializedName("image")
    val image: Image? = null,

    @field:SerializedName("qrCode")
    val qrCode: PeliQrcodePlaceModel? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("checkoutPin")
    val checkoutPin: CheckoutPin? = null,

    @field:SerializedName("maxCapacity")
    val maxCapacity: Int? = null,

    @field:SerializedName("checkPointName")
    val checkPointName: String? = null,

    @field:SerializedName("location")
    val location: PeliAddressPlaceModel? = null,

    @field:SerializedName("activityType")
    val activityType: String? = null,

    @field:SerializedName("category")
    val category: PeliCategoryPlaceModel? = null
) : Parcelable

@Parcelize
data class PeliCategoryPlaceModel(

    @field:SerializedName("image")
    val image: Image? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("key")
    val key: String? = null
) : Parcelable

@Parcelize
data class PeliVaccineModel(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("vaccineStatus")
    val vaccineStatus: Int? = null,

    @field:SerializedName("vaccineStatusName")
    val vaccineStatusName: String? = null
) : Parcelable

@Parcelize
data class PeliAddressPlaceModel(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("districtId")
    val districtId: String? = null,

    @field:SerializedName("districtName")
    val districtName: String? = null,

    @field:SerializedName("cityName")
    val cityName: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("cityId")
    val cityId: String? = null,

    @field:SerializedName("provinceName")
    val provinceName: String? = null,

    @field:SerializedName("provinceId")
    val provinceId: String? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null
) : Parcelable
