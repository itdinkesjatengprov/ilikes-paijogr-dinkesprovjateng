package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaskesModel(
    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("medical_record_number")
    val medicalRecordNumber: String? = null,

    @field:SerializedName("clinic")
    val clinic: String?  = null,

    @field:SerializedName("doctor")
    val doctor: String? = null

) : Parcelable