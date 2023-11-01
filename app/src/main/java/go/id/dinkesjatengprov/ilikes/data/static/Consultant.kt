package go.id.dinkesjatengprov.ilikes.data.static

import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.ConsultationModel
import go.id.dinkesjatengprov.ilikes.data.model.FaskesModel
import go.id.dinkesjatengprov.ilikes.data.model.TreatmentModel

val consultantList = arrayListOf<ConsultationModel>(
    ConsultationModel(
        name = "Halodoc",
        url = "https://halodoc.com",
        icon = R.drawable.img_halodoc,
        isConsultation = true,
        isMedicine = true,
        isMedicineRedeem = true
    ),
    ConsultationModel(
        name = "Klikdokter",
        url = "https://klikdokter.com",
        icon = R.drawable.img_klikdokter,
        isConsultation = true,
        isMedicine = true
    ),
    ConsultationModel(
        name = "Alodokter",
        url = "https://alodokter.com",
        icon = R.drawable.img_alodokter,
        isConsultation = true
    ),
    ConsultationModel(
        name = "Konsula",
        url = "https://konsula.com",
        icon = R.drawable.img_konsula,
        isConsultation = true
    ),
    ConsultationModel(
        name = "Dokter.id",
        url = "https://dokter.id",
        icon = R.drawable.img_dokterid,
        isConsultation = true
    ),
)