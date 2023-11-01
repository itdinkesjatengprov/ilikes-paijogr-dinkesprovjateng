package go.id.dinkesjatengprov.ilikes.data.model

data class ConsultationModel(
    val name: String,

    val url: String? = null,

    val icon: Int,

    val isConsultation: Boolean = false,
    val isMedicine: Boolean = false,
    val isMedicineRedeem: Boolean = false
)
