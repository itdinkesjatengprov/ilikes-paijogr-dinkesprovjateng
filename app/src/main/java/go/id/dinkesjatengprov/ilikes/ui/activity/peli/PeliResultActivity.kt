package go.id.dinkesjatengprov.ilikes.ui.activity.peli

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.pedulilindungi.PeliResultModel
import go.id.dinkesjatengprov.ilikes.databinding.ActivityPeliResultBinding
import go.id.dinkesjatengprov.ilikes.helper.PEDULI_LINDUNGI
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_EE_dd_MMM_yyyy_hh_mm_ss
import go.id.dinkesjatengprov.ilikes.helper.parser.SDF_TYPE_UTC
import go.id.dinkesjatengprov.ilikes.helper.parser.parseDatestringToString
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class PeliResultActivity : BaseActivity<ActivityPeliResultBinding, PeliViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeliResultBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(PeliViewModel::class.java)
        setupObserver()

        val data = intent.getParcelableExtra<PeliResultModel>(PEDULI_LINDUNGI)
        binding?.aprTvName?.text = viewModel?.repository?.getSharedPrefManager()?.namePeli
        binding?.aprTvNik?.text = viewModel?.repository?.getSharedPrefManager()?.nikPeli
        binding?.aprTvLocation?.text = data?.place?.name
        binding?.aprTvTime?.text = parseDatestringToString(
            data?.checkInTime,
            SDF_TYPE_UTC,
            SDF_TYPE_EE_dd_MMM_yyyy_hh_mm_ss
        )

        when(data?.userStatus){
            "green" -> {
                binding?.aprClStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_green)
//                binding?.aprTvStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_green)
                binding?.aprTvStatus?.text = "Sudah Vaksin"
            }
            "red" -> {
                binding?.aprClStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_red)
//                binding?.aprTvStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_red)
                binding?.aprTvStatus?.text = "Belum Vaksin"
            }
            "yellow" ->{
                binding?.aprClStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_yellow)
//                binding?.aprTvStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_yellow)
                binding?.aprTvStatus?.text = "Baru Vaksin 1x"
            }
            else -> {
                binding?.aprClStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_black)
//                binding?.aprTvStatus?.backgroundTintList = resources.getColorStateList(R.color.tint_color_black)
                binding?.aprTvStatus?.text = "Positif Covid19"
            }
        }
    }

    private fun setupObserver() {

    }
}