package go.id.dinkesjatengprov.ilikes.data.static

import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.ConsultationModel
import go.id.dinkesjatengprov.ilikes.data.model.FaskesModel
import go.id.dinkesjatengprov.ilikes.data.model.TelemedicineModel
import go.id.dinkesjatengprov.ilikes.data.model.TreatmentModel

val telemedicineList = arrayListOf<TelemedicineModel>(
    TelemedicineModel(
        name = "RSJD dr.RM.Soedjarwadi Prov Jateng",
        url = "https://telemedisin.rsjd-sujarwadi.com/",
        icon = R.drawable.img_telemedicine_soedjarwadi
    ),
    TelemedicineModel(
        name = "RSUD Kelet Provinsi Jateng",
        url = "https://pelayanan.rsudkelet.co.id/",
        icon = R.drawable.img_telemedicine_kelet
    ),
    TelemedicineModel(
        name = "RSUD Margono Purwokerto",
        url = "https://play.google.com/store/apps/details?id=com.margono.sim.rsmsapp",
        icon = R.drawable.img_telemedicine_margono
    ),
    TelemedicineModel(
        name = "RSJD Surakarta",
        url = "https://rsjd-surakarta.jatengprov.go.id/telemedicine-rsjd-surakarta/",
        icon = R.drawable.img_telemedicine_surakarta
    ),
)