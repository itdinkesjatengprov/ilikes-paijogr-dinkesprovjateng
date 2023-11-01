package go.id.dinkesjatengprov.ilikes.helper.ui

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import java.util.*

/**
 * Show Toast with message
 * */
fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Show Toast with Under Development message
 * */
fun Context.showToastBuilding() {
    Toast.makeText(this, "Fitur sedang dikembangkan", Toast.LENGTH_SHORT).show()
}

/**
 * used to validate empty text or not on TextInputLayout
 * */
fun TextInputLayout.isNotEmpty(): Boolean {
    if (editText?.text.isNullOrBlank()) {
        error = "Tidak boleh kosong"
        errorIconDrawable = null
        requestFocus()
        return false
    }
    isErrorEnabled = false
    return true
}

/**
 * used to validate email format on TextInputLayout
 * */
fun TextInputLayout.isEmailValid(): Boolean {
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText?.text.toString()).matches()) {
        error = "Format email tidak valid"
        requestFocus()
        return false
    }
    isErrorEnabled = false
    return true
}

/**
 * used to validate the length of the text has reached the minimum or not on TextInputLayout
 *
 * @param min is minimum length/digits
 * */
fun TextInputLayout.isLengthLess(min: Int): Boolean {
    if (editText?.text?.length ?: 0 < min) {
        error = "Jumlah digit masih kurang (Minimal $min Karakter)"

        requestFocus()
        return false
    }
    isErrorEnabled = false
    return true
}

/**
 * Use to open DatePicker
 *
 * @param datePickerListener is listener to catch the result
 * @param calendar is default calendar when datepicker open
 * */
fun Context.openDatePicker(
    datePickerListener: DatePickerDialog.OnDateSetListener,
    calendar: Calendar
) {
    DatePickerDialog(
        this,
        datePickerListener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

/**
 * Melakukan pengecekan karakter setiap mengetik,
 * apakah panjang karakter sedah sesuai yang diinginkan
 *
 * @param length panjang minimal karakter yang diinginkan
 * @param msg pesan error jika panjang karakter kurang
 *
 * */
fun TextInputLayout?.textWatcherLengthless(msg: String, length: Int) {
    this?.editText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null) {
                if (p0.length < length) {
                    error = msg
                } else {
                    isErrorEnabled = false
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    })
}