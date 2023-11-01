package go.id.dinkesjatengprov.ilikes.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import go.id.dinkesjatengprov.ilikes.R
import java.util.*

class DialogYearPicker : DialogFragment() {
    private var listener: OnDateSetListener? = null

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater: LayoutInflater = activity?.layoutInflater!!
        val cal: Calendar = Calendar.getInstance()
        val dialog: View = inflater.inflate(R.layout.dialog_year_picker, null)

        val yearPicker = dialog.findViewById(R.id.picker_year) as NumberPicker
        val year: Int = cal.get(Calendar.YEAR)
        yearPicker.minValue = MIN_YEAR
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year
        builder.setView(dialog) // Add action buttons
            .setPositiveButton("Ok") { dialog, id ->
                listener!!.onDateSet(null, yearPicker.value, 0, 0)
            }
            .setNegativeButton("Batal") { dialog, id ->
                this.dialog?.cancel()
            }
        return builder.create()
    }

    companion object {
        private const val MAX_YEAR = 2099
        private const val MIN_YEAR = 1990
    }
}