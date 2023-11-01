package go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseBackground
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseColor
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseText
import go.id.dinkesjatengprov.ilikes.data.static.typeLicenseTubel
import go.id.dinkesjatengprov.ilikes.databinding.ActivityMainTubelBinding
import go.id.dinkesjatengprov.ilikes.helper.LICENSE
import go.id.dinkesjatengprov.ilikes.helper.LICENSE_TYPE
import go.id.dinkesjatengprov.ilikes.helper.TUBEL
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk.DocumentNewStrttkActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class MainTubelActivity : BaseActivity<ActivityMainTubelBinding, TubelViewModel>(),
    View.OnClickListener {

    var license: LicenseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTubelBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(TubelViewModel::class.java)
        setupObserver()

        initView()

        binding?.amsBtnCreate?.setOnClickListener(this)
        binding?.amsBtnDocument?.setOnClickListener(this)
    }

    private fun initView() {
        //Mendapatkan Data Perijinan yang saat ini aktif dari halaman sebelumnya
        license = intent.getParcelableExtra(TUBEL)

        //Cek dan Tempelkan data license yang saat ini aktif (jika ada) pada tampilan
        if (license == null) {
            binding?.amsGroupDocument?.visibility = View.GONE
            binding?.amsTvMessage?.visibility = View.VISIBLE
//            binding?.amsBtnCreate?.visibility = View.VISIBLE
        } else {
            binding?.amsGroupDocument?.visibility = View.VISIBLE
            binding?.amsTvMessage?.visibility = View.GONE
//            binding?.amsBtnCreate?.visibility = View.GONE
            binding?.amsTvStatus?.text = setStatusLicenseText(license?.status)
            binding?.amsTvStatus?.background = setStatusLicenseBackground(license?.status)
            binding?.amsTvStatus?.setTextColor(setStatusLicenseColor(license?.status))
            binding?.amsTvId?.text = license?.id
        }
    }

    private fun setupObserver() {
        viewModel?.tubelActive?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    license = it.data
                    initView()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    showToast("Gagal mendapatkan status terbaru dokumen")
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.amsBtnCreate -> {
                startActivity(Intent(this, FormTubelActivity::class.java))
            }
            binding?.amsBtnDocument -> {
                val intent = Intent(this, DocumentNewStrttkActivity::class.java)
                intent.putExtra(LICENSE, license)
                //todo type lisensi masih statis, type lisensi tidak ada di respon
                intent.putExtra(LICENSE_TYPE, typeLicenseTubel.get(0))
                startActivity(intent)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel?.getActiveTubel()
    }
}