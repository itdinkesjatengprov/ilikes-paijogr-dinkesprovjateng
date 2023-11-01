package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.AddressAutoCompleteAdapter
import go.id.dinkesjatengprov.ilikes.adapter.ClinicAutoCompleteAdapter
import go.id.dinkesjatengprov.ilikes.adapter.DoctorRecyclerViewAdapter
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.ClinicModel
import go.id.dinkesjatengprov.ilikes.data.model.HealthCareModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService7Binding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class RegisterService7Activity : BaseActivity<ActivityRegisterService7Binding, RegisterServiceViewModel>(), View.OnClickListener {

    var bookingModel: BookingModel? = null

    var selectedClinic : ClinicModel? = null
    var listClinic : ArrayList<ClinicModel>? = arrayListOf()
    lateinit var clinicAutoCompleteAdapter: ClinicAutoCompleteAdapter

    lateinit var doctorAdapter : DoctorRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService7Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        bookingModel = intent.getParcelableExtra(BOOKING_MODEL)

        //Setting Action Bat
        supportActionBar?.title = "Pilih Dokter"
        enableHomeButton()

        initView()

        binding?.regBtnNext?.setOnClickListener(this)
    }

    private fun initView() {
        binding?.regTvFaskesName?.text = bookingModel?.healthCareService?.faskes?.name
        binding?.regTvFaskesAddress?.text = bookingModel?.healthCareService?.faskes?.address

        viewModel?.getClinic(bookingModel?.healthCareService?.faskes?.id)
    }

    private fun settingAutoCompleteClinic() {
        val dummyClinic = createDummyListClinic("dummyClinic.json")
        listClinic = dummyClinic

        clinicAutoCompleteAdapter = ClinicAutoCompleteAdapter(this, listClinic)
        binding?.regMactClinic?.setAdapter(clinicAutoCompleteAdapter)
        binding?.regMactClinic?.setOnItemClickListener { parent, view, position, id ->
            if (selectedClinic != clinicAutoCompleteAdapter.getItem(position)){
                selectedClinic = clinicAutoCompleteAdapter.getItem(position)
                resetRecyclerView()
            }
        }

        if (listClinic?.isNotEmpty()==true){
            selectedClinic = listClinic?.get(0)
            resetRecyclerView()
        }
    }

    private fun resetRecyclerView() {
        binding?.regMactClinic?.setText(selectedClinic?.name)

        doctorAdapter = DoctorRecyclerViewAdapter(selectedClinic)
        binding?.regRvDoctor?.layoutManager = LinearLayoutManager(this)
        binding?.regRvDoctor?.adapter = doctorAdapter
    }

    private fun setupObserver() {
        viewModel?.clinic?.observe(this){
            when(it.statusRequest){
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    showToast("Berhasil mendapatkan klinik")
                    settingAutoCompleteClinic()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal mendapatkan Daftar Klinik")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setListenerButtonPrimary(object : DialogMessageListener{
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                viewModel?.getClinic(bookingModel?.healthCareService?.faskes?.id)
                            }
                        })
                        .setDialogCancelable(false)
                        .showDialog()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.regBtnNext -> {
                if (doctorAdapter.selectedDoctor==null){
                    showToast("Pilih Dokter terlebih dahulu")
                }else{
                    val healthCareModel = bookingModel?.healthCareService?.copy(
                        faskes = bookingModel?.healthCareService?.faskes?.copy(
                            clinic = selectedClinic?.name,
                            doctor = doctorAdapter.selectedDoctor?.name
                        )
                    )

                    val intent = Intent(this, RegisterService8Activity::class.java)
                    intent.putExtra(BOOKING_MODEL, bookingModel?.copy(healthCareService = healthCareModel))
                    intent.putExtra(DOCTOR_MODEL, doctorAdapter.selectedDoctor)
                    startActivity(intent)
                }
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