package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LicenseModel(

    @field:SerializedName("note")
    val note: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("list_document")
    val listDocument: ArrayList<LicenseDocumentModel>? = null,

    @field:SerializedName("identity")
    val identity: LicenseIdentityModel? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("document_type")
    val documentType: String? = null,
) : Parcelable
