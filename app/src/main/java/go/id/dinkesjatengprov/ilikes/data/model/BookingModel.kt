package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingModel(

    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("code_booking")
    var codeBooking: String? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("patient_identity")
    var patientIdentity: PatientModel? = PatientModel(),

    @field:SerializedName("father")
    var father: PatientModel? = PatientModel(),

    @field:SerializedName("mother")
    var mother: PatientModel? = PatientModel(),

    @field:SerializedName("health_care_service")
    var healthCareService: HealthCareModel? = HealthCareModel()

) : Parcelable