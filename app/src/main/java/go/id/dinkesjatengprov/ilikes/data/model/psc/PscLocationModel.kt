package go.id.dinkesjatengprov.ilikes.data.model.psc

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PscLocationModel(
    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("coordinates")
    val coordinates: List<Double?>? = null
) : Serializable