package go.id.dinkesjatengprov.ilikes.data.model

import com.google.gson.annotations.SerializedName

data class LicenseResultModel(

	@field:SerializedName("document_number")
	val documentNumber: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("effective_date")
	val effectiveDate: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("expired_date")
	val expiredDate: String? = null
)
