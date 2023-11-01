package go.id.dinkesjatengprov.ilikes.data.model.pmi

import com.google.gson.annotations.SerializedName

data class PmiStatusModel(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("messsage")
	val messsage: String? = null

)
