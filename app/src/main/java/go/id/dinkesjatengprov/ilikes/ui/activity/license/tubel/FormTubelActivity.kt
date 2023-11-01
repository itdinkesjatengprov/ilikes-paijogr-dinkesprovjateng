package go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.model.LicenseIdentityModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.typeLicense
import go.id.dinkesjatengprov.ilikes.data.static.typeLicenseTubel
import go.id.dinkesjatengprov.ilikes.databinding.ActivityFormTubelBinding
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
import java.util.*

class FormTubelActivity : BaseActivity<ActivityFormTubelBinding, TubelViewModel>(),
    View.OnClickListener {

    var selectedType: SelectionModel? = null
    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    var dateOfBirth: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTubelBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(TubelViewModel::class.java)
        setupObserver()

        setTextWatcher()
        setDatePicker()
        binding?.afsTilType?.setEndIconOnClickListener { setTypeSelection() }

        binding?.afsBtnCreate?.setOnClickListener(this)
    }

    private fun setTypeSelection() {
        DialogSelectionCustom(
            this,
            "Pilih Tipe Pendaftaran",
            typeLicenseTubel,
            binding?.afsMactType?.text?.toString()
        ).setListenerButtonPrimary(object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                dialog.dismiss()
                selectedType = selection
                binding?.afsMactType?.setText(selectedType?.name)
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
        binding?.afsMactNip?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 18) {
                        binding?.afsTilNip?.error = "NIP kurang dari 18 digit"
                    } else {
                        binding?.afsTilNip?.isErrorEnabled = false
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
    }

    private fun setupObserver() {
        viewModel?.tubelNew?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    val license = it.data?.copy(
                        identity = LicenseIdentityModel(
                            nik = binding?.afsTilNik?.editText?.text?.toString(),
                            nip = binding?.afsTilNip?.editText?.text?.toString(),
                            name = binding?.afsTilName?.editText?.text?.toString(),
                            sex = if (binding?.afsRbMan?.isChecked == true) "L" else " P",
                            birthPlace = binding?.afsTilPlaceBirth?.editText?.text?.toString(),
                            birthDate = parseDateToCustom(dateOfBirth?.time, SDF_TYPE_yyyy_MM_dd)
                        )
                    )

                    finish()
                    val intent = Intent(this, DocumentNewTubelActivity::class.java)
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
                viewModel?.postTubel(
                    selectedType,
                    binding?.afsTilNik?.editText?.text?.toString(),
                    binding?.afsTilNip?.editText?.text?.toString(),
                    binding?.afsTilName?.editText?.text?.toString(),
                    if (binding?.afsRbMan?.isChecked == true) "L" else " P",
                    binding?.afsTilPlaceBirth?.editText?.text?.toString(),
                    parseDateToCustom(dateOfBirth?.time, SDF_TYPE_yyyy_MM_dd)
                )
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.afsTilType?.isNotEmpty() == false) return false
        if (binding?.afsTilNik?.isNotEmpty() == false) return false
        if (binding?.afsTilNik?.isLengthLess(16) == false) return false
        if (binding?.afsTilNip?.isNotEmpty() == false) return false
        if (binding?.afsTilNip?.isLengthLess(18) == false) return false
        if (binding?.afsTilName?.isNotEmpty() == false) return false
        if (binding?.afsRgSex?.checkedRadioButtonId == -1) {
            showToast("Pilih Jenis kelamin terlebih dahulu")
            return false
        }
        if (binding?.afsTilPlaceBirth?.isNotEmpty() == false) return false
        if (binding?.afsTilDate?.isNotEmpty() == false) return false
        return true
    }
}