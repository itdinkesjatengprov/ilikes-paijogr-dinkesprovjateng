package go.id.dinkesjatengprov.ilikes.ui.activity.psc

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityPscBinding
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage

class PscActivity : BaseActivity<ActivityPscBinding, PscViewModel>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPscBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(PscViewModel::class.java)
        setupObserver()

        if (viewModel?.repository?.getSharedPrefManager()?.isLoggedInPsc == true) {
            goToReportActivity()
        }

        binding?.apBtnNext?.setOnClickListener(this)
        binding?.apBtnGuest?.setOnClickListener(this)
        binding?.apTvDownload?.setOnClickListener(this)
    }

    private fun goToReportActivity() {
        finish()
        val intent = Intent(this, PscReportActivity::class.java)
        startActivity(intent)
    }

    private fun setupObserver() {
        viewModel?.account?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    goToReportActivity()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Login")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.apBtnNext -> {
                if (!validation()) return
                viewModel?.login(
                    username = binding?.apTilUsername?.editText?.text.toString(),
                    password = binding?.apTilPassword?.editText?.text.toString()
                )
            }
            binding?.apBtnGuest -> {
                viewModel?.login(
                    username = "noname@gmail.com",
                    password = "123456"
                )
            }
            binding?.apTvDownload -> {
                val packagePSC = "id.tanggap.pscindonesia"

                try {
                    val urlApps = "market://details?id="
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("$urlApps$packagePSC")))
                } catch (e: ActivityNotFoundException) {
                    val urlPlayStore = "https://play.google.com/store/apps/details?id="
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("$urlPlayStore$packagePSC")))
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.apTilUsername?.isNotEmpty() == false) return false
        if (binding?.apTilPassword?.isNotEmpty() == false) return false
        return true
    }
}