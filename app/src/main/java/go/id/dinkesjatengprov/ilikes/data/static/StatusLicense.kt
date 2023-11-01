package go.id.dinkesjatengprov.ilikes.data.static

import android.content.Context
import android.graphics.drawable.Drawable
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel

val typeLicense: ArrayList<SelectionModel> = arrayListOf(
    SelectionModel(id = "New", name = "Baru"),
    SelectionModel(id = "Extend", name = "Perpanjangan"),
    SelectionModel(id = "Switch Level", name = "Ganti Jenjang")
)

val educationLicense: ArrayList<SelectionModel> = arrayListOf(
    SelectionModel(id = "D3 Farmasi", name = "D3 Farmasi"),
    SelectionModel(id = "D3 Analis Farmasi dan Makanan", name = "D3 Analis Farmasi dan Makanan"),
    SelectionModel(id = "Sarjana Farmasi", name = "Sarjana Farmasi"),
)

val typeLicenseTubel: ArrayList<SelectionModel> = arrayListOf(
    SelectionModel(id = "New", name = "Baru"),
    SelectionModel(id = "Extend", name = "Perpanjangan"),
    SelectionModel(id = "Termination", name = "Pemberhentian"),
    SelectionModel(id = "Replacement", name = "Pergantian")
)

fun setStatusLicenseText(status: String?): String {
    return when (status?.toUpperCase()) {
        "Draft".toUpperCase() -> "Pemberkasan"
        "Submitted".toUpperCase() -> "Pembuatan Dokumen"
        "Approved Validation".toUpperCase() -> "Pengecekan Berkas"
        "Approved Dinkes".toUpperCase() -> "Menunggu Persetujuan"
        "Signed Head Of Departement".toUpperCase() -> "Menunggu Tanda Tangan"
        "Done".toUpperCase() -> "Dokumen Selesai"
        "Returned".toUpperCase() -> "Dikembalikan"
        "Rejected".toUpperCase() -> "Ditolak"
        "Active".toUpperCase() -> "Aktif"
        "Expired".toUpperCase() -> "Kadaluarsa"
        else -> "Tidak Diketahui"
    }
}

fun Context.setStatusLicenseBackground(status: String?): Drawable {
    return when (status?.toUpperCase()) {
        "Draft".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_yellow)
        "Submitted".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_blue)
        "Approved Validation".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_grey)
        "Approved Dinkes".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_blue)
        "Signed Head Of Departement".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_blue)
        "Done".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_blue2)
        "Returned".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_red)
        "Rejected".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_red)
        "Active".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_blue)
        "Expired".toUpperCase() -> resources.getDrawable(R.drawable.bg_license_process_red)
        else -> resources.getDrawable(R.drawable.bg_license_process_grey)
    }
}

fun Context.setStatusLicenseColor(status: String?): Int {
    return when (status?.toUpperCase()) {
        "Draft".toUpperCase() -> resources.getColor(R.color.basic_yellow)
        "Submitted".toUpperCase() -> resources.getColor(R.color.blue)
        "Approved Validation".toUpperCase() -> resources.getColor(R.color.grey)
        "Approved Dinkes".toUpperCase() -> resources.getColor(R.color.blue)
        "Signed Head Of Departement".toUpperCase() -> resources.getColor(R.color.blue)
        "Done".toUpperCase() -> resources.getColor(R.color.basic_blue)
        "Returned".toUpperCase() -> resources.getColor(R.color.basic_red)
        "Rejected".toUpperCase() -> resources.getColor(R.color.basic_red)
        "Active".toUpperCase() -> resources.getColor(R.color.blue)
        "Expired".toUpperCase() -> resources.getColor(R.color.basic_red)
        else -> resources.getColor(R.color.grey)
    }
}