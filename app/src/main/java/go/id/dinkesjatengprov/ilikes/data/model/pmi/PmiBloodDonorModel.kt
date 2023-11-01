package go.id.dinkesjatengprov.ilikes.data.model.pmi

import com.google.gson.annotations.SerializedName

data class PmiBloodDonorModel(

	@field:SerializedName("jam_mulai")
	val jamMulai: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String? = null,

	@field:SerializedName("tgl_mu")
	val tglMu: String? = null,

	@field:SerializedName("instansi")
	val instansi: String? = null,

	@field:SerializedName("peruntukan")
	val peruntukan: String? = null
)
