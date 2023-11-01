package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountModel(

    @field:SerializedName("access_token")
    val token: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("expires_in")
    val expiredDate: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone_number")
    val phone: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable

@Parcelize
data class LoginModel(

    @field:SerializedName("phone_number")
    val phone: String? = null,

    @field:SerializedName("password")
    val password: String? = null
) : Parcelable

