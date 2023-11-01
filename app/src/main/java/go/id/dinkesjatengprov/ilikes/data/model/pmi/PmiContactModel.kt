package go.id.dinkesjatengprov.ilikes.data.model.pmi

import com.google.gson.annotations.SerializedName

data class PmiContactModel(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
