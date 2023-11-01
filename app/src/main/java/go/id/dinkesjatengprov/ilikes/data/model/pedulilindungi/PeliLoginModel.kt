package go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi

import com.google.gson.annotations.SerializedName

data class PeliLoginModel(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Long? = null
)
