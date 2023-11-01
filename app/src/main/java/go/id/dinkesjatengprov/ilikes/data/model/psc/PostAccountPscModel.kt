package go.id.dinkesjatengprov.ilikes.data.model.psc

import com.google.gson.annotations.SerializedName

data class PostAccountPscModel(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)
