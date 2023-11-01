package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.PatientRecyclerViewAdapter
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService1Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.createDummyListBooking
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class RegisterService1Activity :
    BaseActivity<ActivityRegisterService1Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var patientAdapter: PatientRecyclerViewAdapter = PatientRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        enableHomeButton()
        supportActionBar?.title = "Pilih Data Pasien"

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        viewModel?.getPatient()

        binding?.regBtnSelectPatient?.setOnClickListener(this)
        binding?.regBtnAddNewPatient?.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel?.patient?.observe(this, Observer {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    val list = createDummyListBooking("dummyBooking.json")
                    patientAdapter = PatientRecyclerViewAdapter(list)
                    binding?.regRv?.layoutManager = LinearLayoutManager(this)
                    binding?.regRv?.adapter = patientAdapter
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnAddNewPatient -> {
                val intent = Intent(this, RegisterService2Activity::class.java)
                startActivity(intent)
            }
            binding?.regBtnSelectPatient -> {
                if (patientAdapter.selectedPatient == null) {
                    showToast("Silahkan pilih pasien terlebih dahulu")
                } else {
                    val intent = Intent(this, RegisterService2Activity::class.java)
                    intent.putExtra(BOOKING_MODEL, patientAdapter.selectedPatient)
                    startActivity(intent)
                }
            }
        }
    }
}