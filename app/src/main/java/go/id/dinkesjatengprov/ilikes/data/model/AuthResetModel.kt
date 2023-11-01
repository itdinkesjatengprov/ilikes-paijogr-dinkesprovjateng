package go.id.dinkesjatengprov.ilikes.data.model

import com.google.gson.annotations.SerializedName

data class AuthResetModel(

    @field:SerializedName("phone_number")
    val phone: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("re_password")
    val rePassword: String
)
