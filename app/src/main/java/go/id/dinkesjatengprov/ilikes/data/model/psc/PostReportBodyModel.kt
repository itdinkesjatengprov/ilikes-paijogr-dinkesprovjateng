package go.id.dinkesjatengprov.ilikes.data.model.psc

import com.google.gson.annotations.SerializedName

data class PostReportBodyModel(

    @field:SerializedName("additionalInformation")
    val additionalInformation: String?,

    @field:SerializedName("approximateAddress")
    val approximateAddress: String?,

    @field:SerializedName("incidentName")
    val incidentName: String?,

    @field:SerializedName("location")
    val location: PscLocationModel?,

    @field:SerializedName("reportImage")
    var reportImage: List<String?>? = null,

    @field:SerializedName("reportedId")
    var reportedId: String? = null,

    @field:SerializedName("stepOrder")
    var stepOrder: Int? = 1
)