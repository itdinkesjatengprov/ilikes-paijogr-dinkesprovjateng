package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.ScheduleDoctorRecyclerViewAdapter
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.DoctorModel
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService7Binding
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService8Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.DOCTOR_MODEL
import go.id.dinkesjatengprov.ilikes.helper.parser.*
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.openDatePicker
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import java.util.*

class RegisterService8Activity :
    BaseActivity<ActivityRegisterService8Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var bookingModel: BookingModel? = null

    lateinit var dateListener: DatePickerDialog.OnDateSetListener
    var date: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService8Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        bookingModel = intent.getParcelableExtra(BOOKING_MODEL)

        //Setting Action Bat
        supportActionBar?.title = "Pilih Jadwal"
        enableHomeButton()

        initView()

        binding?.regBtnNext?.setOnClickListener(this)
    }

    private fun initView() {
        binding?.regTvFaskesName?.text = bookingModel?.healthCareService?.faskes?.name
        binding?.regTvDoctorName?.text = bookingModel?.healthCareService?.faskes?.doctor

        val doctor = intent.getParcelableExtra<DoctorModel>(DOCTOR_MODEL)
        val schedule = parserScheduleDoctor(doctor?.schedule)
        val adapter = ScheduleDoctorRecyclerViewAdapter(schedule)
        binding?.regRvSchedule?.layoutManager = LinearLayoutManager(this)
        binding?.regRvSchedule?.adapter = adapter

        //Set Date Listener
        dateListener = DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
            date = calendar
            date?.set(Calendar.YEAR, p1)
            date?.set(Calendar.MONTH, p2)
            date?.set(Calendar.DATE, p3)
            binding?.regTilDate?.editText?.setText(
                parseDateToCustom(
                    date?.time,
                    SDF_TYPE_dd_MMM_yyyy
                )
            )
        }
        binding?.regTilDate?.setEndIconOnClickListener {
            /* Jika ingin membatasi picker tanggal berdasarkan jadwal, baca :
            * https://learntodroid.com/how-to-disable-dates-in-a-datepicker-for-android/
            *
            * */
            openDatePicker(dateListener, date ?: calendar)
        }
    }

    private fun setupObserver() {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnNext -> {
                if (binding?.regTilDate?.isNotEmpty() == false) return
                val healthCareModel = bookingModel?.healthCareService?.copy(
                    faskes = bookingModel?.healthCareService?.faskes,
                    date = parseDateToCustom(date?.time, SDF_TYPE_yyyy_MM_dd)
                )
                val intent = Intent(this, RegisterService9Activity::class.java)
                intent.putExtra(
                    BOOKING_MODEL,
                    bookingModel?.copy(healthCareService = healthCareModel)
                )
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        if (!helper_confirmation_backpressed) {
            openDialogConfirmationBack()
        } else {
            super.onBackPressed()
        }
    }
}