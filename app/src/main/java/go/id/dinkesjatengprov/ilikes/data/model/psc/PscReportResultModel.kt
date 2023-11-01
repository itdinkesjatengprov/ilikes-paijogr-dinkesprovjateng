package go.id.dinkesjatengprov.ilikes.data.model.psc

import com.google.gson.annotations.SerializedName

data class PscReportResultModel(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("applicationId")
    val applicationId: String? = null,

    @field:SerializedName("application")
    val application: Any? = null,

    @field:SerializedName("additionalInformation")
    val additionalInformation: String? = null,

    @field:SerializedName("approximateAddress")
    val approximateAddress: String? = null,

    @field:SerializedName("approximateLocation")
    val approximateLocation: List<Double?>? = null,

    @field:SerializedName("location")
    val location: PscLocationModel? = null,

    @field:SerializedName("estimatedCasualties")
    val estimatedCasualties: String? = null,

    @field:SerializedName("incidentName")
    val incidentName: String? = null,

    @field:SerializedName("reportImage")
    val reportImage: List<String?>? = null,

    @field:SerializedName("reportedId")
    val reportedId: String? = null,

    @field:SerializedName("stepOrder")
    val stepOrder: Int? = null
)