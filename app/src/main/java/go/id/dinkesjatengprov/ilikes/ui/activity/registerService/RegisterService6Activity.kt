package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.SelectionFaskesRecyclerView
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.FaskesModel
import go.id.dinkesjatengprov.ilikes.data.model.HealthCareModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService6Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.HEALTH_CARE
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage

class RegisterService6Activity :
    BaseActivity<ActivityRegisterService6Binding, RegisterServiceViewModel>() {

    var bookingModel: BookingModel? = null

    var adapter: SelectionFaskesRecyclerView = SelectionFaskesRecyclerView(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService6Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        bookingModel = intent.getParcelableExtra(BOOKING_MODEL)

        //Setting Action Bat
        supportActionBar?.title = "Pilih Lokasi Pemeriksaan"
        enableHomeButton()

        viewModel?.getFaskes()

        binding?.regBtnNext?.setOnClickListener {
            val selectedFaskes = adapter.mSelection
            if (selectedFaskes == null) {
                showToast("Pilih Faskes terlebih dahulu")
            } else {
                val healthCareModel = HealthCareModel(
                    faskes = adapter.mSelection
                )

                val intent = Intent(this, RegisterService7Activity::class.java)
                intent.putExtra(
                    BOOKING_MODEL,
                    bookingModel?.copy(healthCareService = healthCareModel)
                )
                startActivity(intent)
            }
        }
    }

    private fun setupObserver() {
        viewModel?.faskes?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    binding?.regRvFaskes?.layoutManager = LinearLayoutManager(this)
                    adapter = SelectionFaskesRecyclerView(it.data)
                    binding?.regRvFaskes?.adapter = adapter
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

    override fun onBackPressed() {
        if (!helper_confirmation_backpressed) {
            openDialogConfirmationBack()
        } else {
            super.onBackPressed()
        }
    }
}