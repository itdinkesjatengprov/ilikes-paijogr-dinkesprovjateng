package go.id.dinkesjatengprov.ilikes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleDoctorModel(

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("end_time")
    val endTime: String? = null,

    @field:SerializedName("day")
    val day: String? = null
) : Parcelable
