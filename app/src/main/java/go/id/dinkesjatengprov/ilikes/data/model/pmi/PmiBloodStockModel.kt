package go.id.dinkesjatengprov.ilikes.data.model.pmi

import com.google.gson.annotations.SerializedName

data class PmiBloodStockModel(

	@field:SerializedName("tgl_update")
	val tglUpdate: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("golda_b")
	val goldaB: String? = null,

	@field:SerializedName("golda_ab")
	val goldaAb: String? = null,

	@field:SerializedName("pmi")
	val pmi: String? = null,

	@field:SerializedName("golda_o")
	val goldaO: String? = null,

	@field:SerializedName("golda_a")
	val goldaA: String? = null
)
