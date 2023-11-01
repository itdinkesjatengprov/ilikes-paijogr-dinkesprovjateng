package go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi

import com.google.gson.annotations.SerializedName

data class PeliLogin2Model(

	@field:SerializedName("expiredAt")
	val expiredAt: String? = null,

	@field:SerializedName("authToken")
	val authToken: String? = null
)
