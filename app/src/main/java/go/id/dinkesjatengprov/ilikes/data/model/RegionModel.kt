package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegionModel(

    @field:SerializedName("id")
    var id: Long? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("province")
    var province: SelectionModel? = null,

    @field:SerializedName("city")
    var city: SelectionModel? = null,

    @field:SerializedName("district")
    var district: SelectionModel? = null,

    @field:SerializedName("village")
    var village: SelectionModel? = null,

    @field:SerializedName("rt")
    var rt: String? = null,

    @field:SerializedName("rw")
    var rw: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

) : Parcelable