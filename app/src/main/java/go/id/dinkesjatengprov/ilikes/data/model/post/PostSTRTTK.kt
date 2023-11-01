package go.id.dinkesjatengprov.ilikes.data.model.post

import com.google.gson.annotations.SerializedName

data class PostSTRTTK(

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("graduation_year")
    val graduationYear: String? = null,

    @field:SerializedName("place_birth")
    val placeBirth: String? = null,

    @field:SerializedName("birth_date")
    val birthDate: String? = null,

    @field:SerializedName("sex")
    val sex: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("document_type")
    val documentType: String? = null,

    @field:SerializedName("school_type")
    val schoolType: String? = null,

    @field:SerializedName("school_name")
    val schoolName: String? = null
)
