package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientModel(

    @field:SerializedName("nik")
    var nik: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("religion")
    var religion: String? = null,

    @field:SerializedName("sex")
    var sex: String? = null,

    @field:SerializedName("birth_date")
    var birthDate: String? = null,

    @field:SerializedName("education")
    var education: String? = null,

    @field:SerializedName("job")
    var job: String? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("address_ktp")
    var addressKtp: RegionModel? = null,

    @field:SerializedName("address_domicile")
    var addressDomicile: RegionModel? = null
) : Parcelable