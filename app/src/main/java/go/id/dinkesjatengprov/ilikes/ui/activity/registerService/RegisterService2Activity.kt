package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.genders
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService2Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.parser.*
import go.id.dinkesjatengprov.ilikes.helper.ui.isLengthLess
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.openDatePicker
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustom
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustomListener
import java.util.*

class RegisterService2Activity :
    BaseActivity<ActivityRegisterService2Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var bookingModel = BookingModel()
    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    var dateOfBirth: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        val booking = intent.getParcelableExtra<BookingModel>(BOOKING_MODEL)
        bookingModel = booking ?: bookingModel
        initView()

        //Setting Action Bat
        supportActionBar?.title =
            if (bookingModel.id == null) "Tambah Data Pasien Baru" else "Daftar Pasien"
        enableHomeButton()

        //Set TextWatcher NIK
        setTextWatcher()
        //Set Gender Picker
        setGenderPicker()
        //Set DatePicker Date of Birth
        setDatePickerDOB()

        binding?.regBtnNext?.setOnClickListener(this)

        binding?.regTilJob?.setEndIconOnClickListener {
            if (viewModel?.job?.value?.data == null) viewModel?.getJob() else setJobPicker()
        }
    }

    private fun initView() {
        binding?.regMactNik?.setText(bookingModel.patientIdentity?.nik)
        binding?.regMactName?.setText(bookingModel.patientIdentity?.name)
        binding?.regMactGender?.setText(bookingModel.patientIdentity?.sex)
        binding?.regMactDate?.setText(
            parseDatestringToString(
                bookingModel.patientIdentity?.birthDate,
                SDF_TYPE_yyyy_MM_dd,
                SDF_TYPE_dd_MMM_yyyy
            )
        )
        binding?.regMactPhone?.setText(bookingModel.patientIdentity?.phone)
        binding?.regMactJob?.setText(bookingModel.patientIdentity?.job)
    }

    private fun setupObserver() {
        viewModel?.job?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    //Set Job Picker
                    setJobPicker()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Mendapatkan Daftar Pekerjaan")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
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

    private fun setGenderPicker() {
        binding?.regTilGender?.setEndIconOnClickListener {
            DialogSelectionCustom(
                this,
                "Pilih Jenis Kelamin",
                genders,
                binding?.regMactGender?.text?.toString()
            )
                .setListenerButtonPrimary(object : DialogSelectionCustomListener {
                    override fun onClick(
                        dialog: DialogSelectionCustom,
                        selection: SelectionModel?
                    ) {
                        dialog.dismiss()
                        binding?.regMactGender?.setText(selection?.name)
                    }
                })
                .show()
        }
    }

    private fun setDatePickerDOB() {
        val dob = parseToCalendar(bookingModel.patientIdentity?.birthDate, SDF_TYPE_yyyy_MM_dd)

        dateOfBirth = dob
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
            openDatePicker(dateListener, dob)
        }
    }

    private fun setJobPicker() {
        DialogSelectionCustom(
            this,
            "Pilih Pekerjaan",
            viewModel?.job?.value?.data,
            binding?.regMactJob?.text?.toString()
        )
            .setListenerButtonPrimary(object : DialogSelectionCustomListener {
                override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                    dialog.dismiss()
                    binding?.regMactJob?.setText(selection?.name)
                }
            })
            .show()
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

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnNext -> {
                if (!validation()) return
                bookingModel = bookingModel.copy(
                    patientIdentity = bookingModel.patientIdentity?.copy(
                        name = binding?.regMactName?.text.toString(),
                        sex = binding?.regMactGender?.text.toString(),
                        birthDate = parseDateToCustom(dateOfBirth?.time, SDF_TYPE_yyyy_MM_dd),
                        phone = binding?.regMactPhone?.text.toString(),
                        job = binding?.regMactJob?.text.toString()
                    )
                )
                val intent = Intent(this, RegisterService3Activity::class.java)
                intent.putExtra(BOOKING_MODEL, bookingModel)
                startActivity(intent)
            }
        }
    }
}