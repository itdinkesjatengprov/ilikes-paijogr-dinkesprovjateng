package go.id.dinkesjatengprov.ilikes.helper.parser

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

const val LOCALE_IN = "in"
const val LOCALE_ID = "ID"

val locale = Locale(
    LOCALE_IN,
    LOCALE_ID
)

val calendar = Calendar.getInstance()

const val SDF_TYPE_dd_MMM_yyyy = "dd MMM yyyy"
const val SDF_TYPE_yyyy_MM_dd = "yyyy-MM-dd"
const val SDF_TYPE_yyyy_MM_dd_HH_mm = "yyyy_MM_dd_HH_mm"
const val SDF_TYPE_EE_dd_MMM_yyyy_hh_mm_ss = "EEE, dd MMM yyyy, HH:mm:ss"
const val SDF_TYPE_UTC = "yyyy-MM-dd'T'HH:mm:ss"

fun parseDateToCustom(date: Date?, formatResult: String): String {
    var newDate = calendar.time
    if (date != null) newDate = date

    val sdf2 = SimpleDateFormat(
        formatResult, Locale(
            LOCALE_IN,
            LOCALE_ID
        )
    )
    return sdf2.format(newDate)
}

fun parseDatestringToString(
    stringDate: String?,
    firstFormat: String,
    formatResult: String
): String? {
    return if (stringDate == null || stringDate == "") {
        stringDate
    } else {
        val sdf = SimpleDateFormat(firstFormat, Locale.getDefault())
        val parseDate = sdf.parse(stringDate)

        val sdf2 = SimpleDateFormat(
            formatResult, Locale(
                LOCALE_IN,
                LOCALE_ID
            )
        )
        sdf2.format(parseDate)
    }
}

fun parseToCalendar(date: String?, format: String): Calendar{
    return if (date==null){
        calendar
    }else{
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val calendarDoB = Calendar.getInstance()
        calendarDoB.time = sdf.parse(date)
        calendarDoB
    }
}

fun countAge(date: String?, format: String?): Int {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    val calendarDoB = Calendar.getInstance()
    calendarDoB.time = sdf.parse(date)

    var age = calendar.get(Calendar.YEAR) - calendarDoB.get(Calendar.YEAR)
    if (calendar.get(Calendar.DAY_OF_YEAR) < calendarDoB.get(Calendar.DAY_OF_YEAR)) {
        age -= 1
    }
    return age
}

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}

fun currentTimeToLong(): Long {
    return System.currentTimeMillis()
}

fun convertDateToLong(cal: Calendar): Long {
    return cal.time.time
}