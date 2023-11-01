package go.id.dinkesjatengprov.ilikes.ui.activity.register

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegister2Binding
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_dd_MMM_yyyy
import go.id.dinkesjatengprov.ilikes.helper.parser.calendar
import go.id.dinkesjatengprov.ilikes.helper.parser.parseDateToCustom
import go.id.dinkesjatengprov.ilikes.helper.ui.isLengthLess
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.openDatePicker
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelection
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionListener
import java.util.*

class Register2Activity : BaseActivity<ActivityRegister2Binding, RegisterViewModel>(), View.OnClickListener {

    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    var dateOfBirth: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Setting Action Bat
        supportActionBar?.title = "Kelengkapan Identitas"
        enableHomeButton()

        //Set Gender Picker
        setGenderPicker()
        setJobPicker()

        //Set DatePicker Date of Birth
        setDatePickerDOB()

        //Set TextWatcher NIK
        setTextWatcher()

        binding?.regBtnNext?.setOnClickListener(this)
    }

    private fun setJobPicker() {
        val job = arrayListOf("Tidak Bekerja", "Pelajar/Mahasiswa", "Wiraswasta", "Wirausaha", "PNS", "TNI", "Polisi", "Nelayan/Perikanan")
        binding?.regTilJob?.setEndIconOnClickListener {
            DialogSelection(this, "Pilih Pekerjaan", job, binding?.regMactJob?.text?.toString())
                .setListenerButtonPrimary(object : DialogSelectionListener {
                    override fun onClick(dialog: DialogSelection, text: String?) {
                        dialog.dismiss()
                        binding?.regMactJob?.setText(text)
                    }
                })
                .show()
        }
    }

    private fun setGenderPicker() {
        val genders = arrayListOf<String>("Laki-laki", "Perempuan")
        binding?.regTilGender?.setEndIconOnClickListener {
            DialogSelection(this, "Pilih Jenis Kelamin", genders, binding?.regMactGender?.text?.toString())
                .setListenerButtonPrimary(object : DialogSelectionListener {
                    override fun onClick(dialog: DialogSelection, text: String?) {
                        dialog.dismiss()
                        binding?.regMactGender?.setText(text)
                    }
                })
                .show()
        }
    }

    private fun setTextWatcher() {
        binding?.regMactNik?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 16) {
                        binding?.regTilNik?.error = "NIK kurang dari 16 digit"
                    } else {
                        binding?.regTilNik?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding?.regMactPhone?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 10) {
                        binding?.regTilPhone?.error = "No. HP kurang dari 10 digit"
                    } else {
                        binding?.regTilPhone?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setDatePickerDOB() {
        dateOfBirth = calendar
        dateListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            dateOfBirth?.set(Calendar.YEAR, p1)
            dateOfBirth?.set(Calendar.MONTH, p2)
            dateOfBirth?.set(Calendar.DATE, p3)
            binding?.regTilDate?.editText?.setText(
                parseDateToCustom(
                    dateOfBirth?.time,
                    SDF_TYPE_dd_MMM_yyyy
                )
            )
        }
        binding?.regTilDate?.setEndIconOnClickListener {
            openDatePicker(dateListener, calendar)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnNext -> {
                if (!validation()) {
                    return
                }
                startActivity(Intent(this, Register3Activity::class.java))
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.regTilNik?.isNotEmpty() == false) return false
        if (binding?.regTilNik?.isLengthLess(16) == false) return false
        if (binding?.regTilName?.isNotEmpty() == false) return false
        if (binding?.regTilGender?.isNotEmpty() == false) return false
        if (binding?.regTilDate?.isNotEmpty() == false) return false
        if (binding?.regTilPhone?.isNotEmpty() == false) return false
        if (binding?.regTilPhone?.isLengthLess(10) == false) return false

        return true
    }

    override fun onBackPressed() {
        if (!helper_confirmation_backpressed) {
            openDialogConfirmationBack()
        } else {
            super.onBackPressed()
        }
    }
}