package go.id.dinkesjatengprov.ilikes.helper.parser

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import go.id.dinkesjatengprov.ilikes.data.model.ScheduleDoctorModel
import java.util.*


fun parserScheduleDoctor(doctorSchedule: ArrayList<ScheduleDoctorModel>?): ArrayList<ScheduleDoctorModel> {
    val senin = doctorSchedule?.find { it.day == "Senin" } ?: ScheduleDoctorModel(day = "Senin")
    val selasa = doctorSchedule?.find { it.day == "Selasa" } ?: ScheduleDoctorModel(day = "Selasa")
    val rabu = doctorSchedule?.find { it.day == "Rabu" } ?: ScheduleDoctorModel(day = "Rabu")
    val kamis = doctorSchedule?.find { it.day == "Kamis" } ?: ScheduleDoctorModel(day = "Kamis")
    val jumat = doctorSchedule?.find { it.day == "Jumat" } ?: ScheduleDoctorModel(day = "Jumat")
    val sabtu = doctorSchedule?.find { it.day == "Sabtu" } ?: ScheduleDoctorModel(day = "Sabtu")
    val minggu = doctorSchedule?.find { it.day == "Minggu" } ?: ScheduleDoctorModel(day = "Minggu")

    return arrayListOf<ScheduleDoctorModel>(senin, selasa, rabu, kamis, jumat, sabtu, minggu)
}

/**
 * Mengecek apakah sebuah String terdapat sebuah kata tertentu
 * @param word sebuah kata yang divalidasi
 *
 * */
fun String?.isLowerContains(word: String): Boolean {
    return this?.lowercase(Locale.getDefault())
        ?.contains(word.lowercase(Locale.getDefault())) == true
}

fun Context.latlongToAddress(latitude: Double?, longitude: Double?): String {
    val addresses: List<Address>
    val geocoder: Geocoder = Geocoder(this, Locale.getDefault())

    if (latitude == null || longitude == null) {
        return "-"
    } else {
        addresses = geocoder.getFromLocation(
            latitude,
            longitude!!,
            1
        ) as List<Address> // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        val address: String =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        val city: String = addresses[0].getLocality()
        val state: String = addresses[0].getAdminArea()
        val country: String = addresses[0].getCountryName()
        val postalCode: String = addresses[0].getPostalCode()
        val knownName: String = addresses[0].getFeatureName() // Only if available else return NULL

        Log.d("4DDRESS", "latlongToAddress: $city, $state,, $country, $postalCode, $knownName")
        return address
    }

}