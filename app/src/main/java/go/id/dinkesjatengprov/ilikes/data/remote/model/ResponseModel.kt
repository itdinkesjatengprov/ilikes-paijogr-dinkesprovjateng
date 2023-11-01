package go.id.dinkesjatengprov.ilikes.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseModel<out T>(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("data")
    val data: T? = null
)