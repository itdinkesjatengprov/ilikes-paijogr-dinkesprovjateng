package go.id.dinkesjatengprov.ilikes.data.model.pmi

import com.google.gson.annotations.SerializedName

data class PmiResponseModel<out T>(
    @field:SerializedName("status")
    val status: PmiStatusModel? = null,

    @field:SerializedName("data")
    val data: T? = null
)