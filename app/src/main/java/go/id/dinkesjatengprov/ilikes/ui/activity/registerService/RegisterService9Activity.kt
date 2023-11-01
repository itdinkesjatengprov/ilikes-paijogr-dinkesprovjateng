package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService8Binding
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService9Binding
import go.id.dinkesjatengprov.ilikes.helper.BOOKING_MODEL
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_yyyy_MM_dd
import go.id.dinkesjatengprov.ilikes.helper.parser.parseDateToCustom
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class RegisterService9Activity : BaseActivity<ActivityRegisterService9Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var bookingModel: BookingModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService9Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        bookingModel = intent.getParcelableExtra(BOOKING_MODEL)

        //Setting Action Bat
        supportActionBar?.title = "Pilih Jadwal"
        enableHomeButton()

        binding?.regRg?.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.reg_rb_manual -> {
                    binding?.regTvBpjs?.visibility = View.GONE
                    binding?.regTilBpjs?.visibility = View.GONE
                    binding?.regMactBpjs?.setText(null)
                }
                R.id.reg_rb_bpjs -> {
                    binding?.regTvBpjs?.visibility = View.VISIBLE
                    binding?.regTilBpjs?.visibility = View.VISIBLE
                }
            }
        }

        binding?.regBtnNext?.setOnClickListener(this)
    }

    private fun setupObserver() {

    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.regBtnNext -> {
                if (binding?.regRg?.checkedRadioButtonId==-1){
                    showToast("Pilih jenis pembayaran terlebih dahulu")
                    return
                }else{
                    if (binding?.regRbBpjs?.isChecked==true){
                        if (binding?.regTilBpjs?.isNotEmpty()==false) return
                    }
                }
                val healthCareModel = bookingModel?.healthCareService?.copy(
                    faskes = bookingModel?.healthCareService?.faskes,
                    date = bookingModel?.healthCareService?.date,
                    bpjsNumber = binding?.regMactBpjs?.text.toString(),
                    type = if (binding?.regRbManual?.isChecked==true) "Manual" else "Bpjs"
                )
                bookingModel = bookingModel?.copy(healthCareService = healthCareModel)
                showToast("Upload Data")
                Log.d("R3GISTER", "onClick: "+Gson().toJson(bookingModel))
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