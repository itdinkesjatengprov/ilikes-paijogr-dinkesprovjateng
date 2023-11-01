package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthCareModel(

    @field:SerializedName("faskes")
    var faskes: FaskesModel?  = null,

    @field:SerializedName("bpjs_number")
    var bpjsNumber: String? = null,

    @field:SerializedName("date")
    var date: String? = null,

    @field:SerializedName("type")
    var type: String? = null,

    @field:SerializedName("note")
    var note: String? = null

) : Parcelable