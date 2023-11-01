package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.PatientModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService5Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.parser.*
import go.id.dinkesjatengprov.ilikes.helper.ui.isLengthLess
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.openDatePicker
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustom
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionCustomListener
import java.util.*

class RegisterService5Activity :
    BaseActivity<ActivityRegisterService5Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var bookingModel = BookingModel()
    lateinit var dateListenerFather: DatePickerDialog.OnDateSetListener
    var dateOfBirthFather: Calendar? = null
    lateinit var dateListenerMother: DatePickerDialog.OnDateSetListener
    var dateOfBirthMother: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService5Binding.inflate(layoutInflater)
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
        //Set DatePicker Date of Birth
        setDatePickerDOB()

        viewModel?.getJob()

        binding?.regTilJobFather?.setEndIconOnClickListener {
            if (viewModel?.job?.value?.data == null) viewModel?.getJob() else setJobPickerFather()
        }

        binding?.regTilJobMother?.setEndIconOnClickListener {
            if (viewModel?.job?.value?.data == null) viewModel?.getJob() else setJobPickerMother()
        }

        binding?.regBtnNext?.setOnClickListener(this)

    }

    private fun setJobPickerFather() {
        DialogSelectionCustom(
            this,
            "Pilih Pekerjaan",
            viewModel?.job?.value?.data,
            binding?.regMactJobFather?.text?.toString()
        ).setListenerButtonPrimary(object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                dialog.dismiss()
                binding?.regMactJobFather?.setText(selection?.name)
            }
        }).show()
    }

    private fun setJobPickerMother() {
        DialogSelectionCustom(
            this,
            "Pilih Pekerjaan",
            viewModel?.job?.value?.data,
            binding?.regMactJobMother?.text?.toString()
        ).setListenerButtonPrimary(object : DialogSelectionCustomListener {
            override fun onClick(dialog: DialogSelectionCustom, selection: SelectionModel?) {
                dialog.dismiss()
                binding?.regMactJobMother?.setText(selection?.name)
            }
        }).show()
    }

    private fun setDatePickerDOB() {
        val dobFather = parseToCalendar(bookingModel.father?.birthDate, SDF_TYPE_yyyy_MM_dd)

        dateOfBirthFather = dobFather
        dateListenerFather = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            dateOfBirthFather?.set(Calendar.YEAR, p1)
            dateOfBirthFather?.set(Calendar.MONTH, p2)
            dateOfBirthFather?.set(Calendar.DATE, p3)
            binding?.regTilDateFather?.editText?.setText(
                parseDateToCustom(
                    dateOfBirthFather?.time,
                    SDF_TYPE_dd_MMM_yyyy
                )
            )
        }
        binding?.regTilDateFather?.setEndIconOnClickListener {
            openDatePicker(dateListenerFather, dobFather)
        }

        val dobMother = parseToCalendar(bookingModel.father?.birthDate, SDF_TYPE_yyyy_MM_dd)

        dateOfBirthMother = dobMother
        dateListenerMother = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            dateOfBirthMother?.set(Calendar.YEAR, p1)
            dateOfBirthMother?.set(Calendar.MONTH, p2)
            dateOfBirthMother?.set(Calendar.DATE, p3)
            binding?.regTilDateMother?.editText?.setText(
                parseDateToCustom(
                    dateOfBirthMother?.time,
                    SDF_TYPE_dd_MMM_yyyy
                )
            )
        }
        binding?.regTilDateMother?.setEndIconOnClickListener {
            openDatePicker(dateListenerMother, dobMother)
        }
    }

    private fun setTextWatcher() {
        binding?.regMactNikFather?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 16) {
                        binding?.regTilNikFather?.error = "NIK kurang dari 16 digit"
                    } else {
                        binding?.regTilNikFather?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding?.regMactNikMother?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 16) {
                        binding?.regTilNikMother?.error = "NIK kurang dari 16 digit"
                    } else {
                        binding?.regTilNikMother?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun initView() {
        binding?.regMactNikFather?.setText(bookingModel.father?.nik)
        binding?.regMactNameFather?.setText(bookingModel.father?.name)
        binding?.regMactDateFather?.setText(
            parseDatestringToString(
                bookingModel.father?.birthDate,
                SDF_TYPE_yyyy_MM_dd,
                SDF_TYPE_dd_MMM_yyyy
            )
        )
        binding?.regMactJobFather?.setText(bookingModel?.father?.job)

        binding?.regMactNikMother?.setText(bookingModel.mother?.nik)
        binding?.regMactNameMother?.setText(bookingModel.mother?.name)
        binding?.regMactDateMother?.setText(
            parseDatestringToString(
                bookingModel.mother?.birthDate,
                SDF_TYPE_yyyy_MM_dd,
                SDF_TYPE_dd_MMM_yyyy
            )
        )
        binding?.regMactJobMother?.setText(bookingModel?.mother?.job)
    }

    private fun setupObserver() {
        viewModel?.job?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    showToast("Berhasil mendapatkan daftar pekerjaan")
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

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnNext -> {
                if (!validation()) return
                val father = PatientModel(
                    nik = binding?.regMactNikFather?.text.toString(),
                    name = binding?.regMactNameFather?.text.toString(),
                    job = binding?.regMactJobFather?.text.toString(),
                    birthDate = parseDateToCustom(dateOfBirthFather?.time, SDF_TYPE_yyyy_MM_dd)
                )
                val mother = PatientModel(
                    nik = binding?.regMactNikMother?.text.toString(),
                    name = binding?.regMactNameMother?.text.toString(),
                    job = binding?.regMactJobMother?.text.toString(),
                    birthDate = parseDateToCustom(dateOfBirthMother?.time, SDF_TYPE_yyyy_MM_dd)
                )
                bookingModel = bookingModel.copy(
                    father = father,
                    mother = mother
                )
                val intent = Intent(this, RegisterService6Activity::class.java)
                intent.putExtra(BOOKING_MODEL, bookingModel)
                startActivity(intent)
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.regTilNikFather?.isNotEmpty() == false) return false
        if (binding?.regTilNikFather?.isLengthLess(16) == false) return false
        if (binding?.regTilNameFather?.isNotEmpty() == false) return false
        if (binding?.regTilDateFather?.isNotEmpty() == false) return false
        if (binding?.regTilJobFather?.isNotEmpty() == false) return false
        if (binding?.regTilNikMother?.isNotEmpty() == false) return false
        if (binding?.regTilNikMother?.isLengthLess(16) == false) return false
        if (binding?.regTilNameMother?.isNotEmpty() == false) return false
        if (binding?.regTilDateMother?.isNotEmpty() == false) return false
        if (binding?.regTilJobMother?.isNotEmpty() == false) return false
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