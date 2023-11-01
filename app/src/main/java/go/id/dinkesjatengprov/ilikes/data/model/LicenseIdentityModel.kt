package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LicenseIdentityModel(

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("nip")
    val nip: String? = null,

    @field:SerializedName("sex")
    val sex: String? = null,

    @field:SerializedName("birth_date")
    val birthDate: String? = null,

    @field:SerializedName("place_birth")
    val birthPlace: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("school_type")
    val schoolType: String? = null,

    @field:SerializedName("school_name")
    val schoolName: String? = null,

    @field:SerializedName("graduation_year")
    val graduationYear: String? = null
) : Parcelable
