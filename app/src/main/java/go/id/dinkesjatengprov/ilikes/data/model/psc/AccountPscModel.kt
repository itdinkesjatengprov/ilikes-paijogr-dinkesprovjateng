package go.id.dinkesjatengprov.ilikes.data.model.psc

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import go.id.dinkesjatengprov.ilikes.data.local.converters.RolesConverters

@Entity(tableName = "tb_account_psc")
data class AccountPscModel(

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("identityNumber")
    val identityNumber: String? = null,

    @TypeConverters(RolesConverters::class)
    @field:SerializedName("roles")
    val roles: List<String?>? = null,

    @field:SerializedName("avatarImageUrl")
    val avatarImageUrl: String? = null,
)
