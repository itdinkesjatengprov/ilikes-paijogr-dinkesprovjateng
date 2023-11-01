package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import go.id.dinkesjatengprov.ilikes.data.model.LicenseIdentityModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.educationLicense
import go.id.dinkesjatengprov.ilikes.data.static.typeLicense
import go.id.dinkesjatengprov.ilikes.databinding.ActivityFormStrttkBinding
import go.id.dinkesjatengprov.ilikes.helper.LICENSE
import go.id.dinkesjatengprov.ilikes.helper.LICENSE_TYPE
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_dd_MMM_yyyy
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_yyyy_MM_dd
import go.id.dinkesjatengprov.ilikes.helper.parser.calendar
import go.id.dinkesjatengprov.ilikes.helper.parser.parseDateToCustom
import go.id.dinkesjatengprov.ilikes.helper.ui.isLengthLess
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.openDatePicker
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustom
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustomListener
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogYearPicker
import java.util.*

class FormStrttkActivity : BaseActivity<ActivityFormStrttkBinding, StrttkViewModel>(),
    View.OnClickListener {

    var selectedType: SelectionModel? = null
    var selectedEducation: SelectionModel? = null
    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    var dateOfBirth: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormStrttkBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(StrttkViewModel::class.java)
        setupObserver()

        viewModel?.getAuth()
//        setTextWatcher()
        setDatePicker()
        binding?.afsTilType?.typeListener()
        binding?.afsTilEducation?.educationListener()
        binding?.afsTilGraduationYear?.graduationYearListener()

        binding?.afsBtnCreate?.setOnClickListener(this)
    }

    fun TextInputLayout.typeListener(){
        setEndIconOnClickListener { setTypeSelection() }
        editText?.setOnClickListener { setTypeSelection() }
    }
    fun TextInputLayout.educationListener(){
        setEndIconOnClickListener { setEducationSelection() }
        editText?.setOnClickListener { setEducationSelection() }
    }
    fun TextInputLayout.graduationYearListener(){
        setEndIconOnClickListener { setDialogYearPicker() }
        editText?.setOnClickListener { setDialogYearPicker() }
    }

    private fun setDialogYearPicker() {
        val dialog = DialogYearPicker()
        dialog.setListener { p0, p1, p2, p3 -> binding?.afsTilGraduationYear?.editText?.setText("$p1") }
        dialog.show(supportFragmentManager, "DIALOG")
    }

    private fun setTypeSelection() {
        DialogSelectionCustom(
            this,
            "Pilih Tipe Pendaftaran",
            typeLicense,
            binding?.afsMactType?.text?.toString()
        ).setListenerButtonPrimary(object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                dialog.dismiss()
                selectedType = selection
                binding?.afsMactType?.setText(selectedType?.name)
            }
        }).show()
    }

    private fun setEducationSelection() {
        DialogSelectionCustom(
            this,
            "Pilih Pendidikan Terakhir",
            educationLicense,
            binding?.afsMactEducation?.text?.toString()
        ).setListenerButtonPrimary(object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                dialog.dismiss()
                selectedEducation = selection
                binding?.afsMactEducation?.setText(selectedEducation?.name)
            }
        }).show()
    }

    private fun setTextWatcher() {
        binding?.afsMactNik?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 16) {
                        binding?.afsTilNik?.error = "NIK kurang dari 16 digit"
                    } else {
                        binding?.afsTilNik?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setDatePicker() {
        dateOfBirth = calendar
        dateListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            dateOfBirth?.set(Calendar.YEAR, p1)
            dateOfBirth?.set(Calendar.MONTH, p2)
            dateOfBirth?.set(Calendar.DATE, p3)
            binding?.afsTilDate?.editText?.setText(
                parseDateToCustom(
                    dateOfBirth?.time,
                    SDF_TYPE_dd_MMM_yyyy
                )
            )
        }
        binding?.afsTilDate?.setEndIconOnClickListener {
            openDatePicker(dateListener, dateOfBirth ?: calendar)
        }
        binding?.afsTilDate?.editText?.setOnClickListener {
            openDatePicker(dateListener, dateOfBirth ?: calendar)
        }
    }

    private fun setupObserver() {
        viewModel?.auth?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.afsTilNik?.editText?.setText(it.data?.nik)
                    binding?.afsTilName?.editText?.setText(it.data?.name)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal mendapatkan data")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }
        viewModel?.newStrttk?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    val license = it.data?.copy(
                        identity = LicenseIdentityModel(
                            nik = binding?.afsTilNik?.editText?.text?.toString(),
                            name = binding?.afsTilName?.editText?.text?.toString(),
                            sex = if (binding?.afsRbMan?.isChecked == true) "L" else " P",
                            birthDate = parseDateToCustom(dateOfBirth?.time, SDF_TYPE_yyyy_MM_dd),
                            birthPlace = binding?.afsTilPlaceBirth?.editText?.text?.toString(),
                            schoolType = selectedEducation?.id,
                            schoolName = binding?.afsTilUniversity?.editText?.text.toString()
                        ),
                        note = "-"
                    )

                    finish()
                    val intent = Intent(this, DocumentNewStrttkActivity::class.java)
                    intent.putExtra(LICENSE, license)
                    intent.putExtra(LICENSE_TYPE, selectedType)
                    startActivity(intent)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.afsBtnCreate -> {
                if (!validation()) return
                viewModel?.postStrttk(
                    selectedType,
                    binding?.afsTilNik?.editText?.text?.toString(),
                    binding?.afsTilName?.editText?.text?.toString(),
                    if (binding?.afsRbMan?.isChecked == true) "L" else " P",
                    binding?.afsTilPlaceBirth?.editText?.text?.toString(),
                    parseDateToCustom(dateOfBirth?.time, SDF_TYPE_yyyy_MM_dd),
                    binding?.afsTilEducation?.editText?.text?.toString(),
                    binding?.afsTilUniversity?.editText?.text?.toString(),
                    binding?.afsTilGraduationYear?.editText?.text?.toString(),
                )
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.afsTilType?.isNotEmpty() == false) return false
        if (binding?.afsTilNik?.isNotEmpty() == false) return false
        if (binding?.afsTilNik?.isLengthLess(16) == false) return false
        if (binding?.afsTilName?.isNotEmpty() == false) return false
        if (binding?.afsRgSex?.checkedRadioButtonId == -1) {
            showToast("Pilih Jenis kelamin terlebih dahulu")
            return false
        }
        if (binding?.afsTilPlaceBirth?.isNotEmpty() == false) return false
        if (binding?.afsTilDate?.isNotEmpty() == false) return false
        if (binding?.afsTilEducation?.isNotEmpty() == false) return false
        if (binding?.afsTilUniversity?.isNotEmpty() == false) return false
        if (binding?.afsTilGraduationYear?.isNotEmpty() == false) return false
        if (binding?.afsTilGraduationYear?.isLengthLess(4) == false) return false
        return true
    }
}