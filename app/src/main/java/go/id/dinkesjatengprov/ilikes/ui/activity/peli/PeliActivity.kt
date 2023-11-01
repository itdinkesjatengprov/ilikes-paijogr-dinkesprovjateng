package go.id.dinkesjatengprov.ilikes.ui.activity.peli

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityPeliBinding
import go.id.dinkesjatengprov.ilikes.helper.HelperPermissionListener
import go.id.dinkesjatengprov.ilikes.helper.RC_PERMISSION
import go.id.dinkesjatengprov.ilikes.helper.helperRequestPermission
import go.id.dinkesjatengprov.ilikes.helper.ui.isLengthLess
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.WebviewActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class PeliActivity : BaseActivity<ActivityPeliBinding, PeliViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeliBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(PeliViewModel::class.java)
        setupObserver()

        setPrivacyPolicyText()
        setTextWatcher()
        checkAppPermission()

        binding?.apBtnNext?.setOnClickListener(this)

    }

    private fun setTextWatcher() {
        binding?.apMactNik?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.length < 16) {
                        binding?.apTilNik?.error = "NIK kurang dari 16 digit"
                    } else {
                        binding?.apTilNik?.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setupObserver() {
        viewModel?.corjat?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal mendapatkan data")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setTextButtonSecondary("Batal")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                            }
                        })
                        .setListenerButtonSecondary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                finish()
                            }
                        })
                        .setDialogCancelable(false)
                        .showDialog()
                }
            }
        }
        viewModel?.peli?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    finish()
                    startActivity(Intent(this, PeliScannerActivity::class.java))
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal masuk akun PeduliLindungi")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    private fun checkAppPermission() {
        helperRequestPermission(object : HelperPermissionListener {
            override fun allPermissionGranted() {
                binding?.apBtnNext?.visibility = View.VISIBLE
                initData()
            }

            override fun anyPermissionDenied() {
                binding?.apBtnNext?.visibility = View.GONE
                checkAppPermission()
            }

            override fun anyPermissionPermantentlyDenied() {
                binding?.apBtnNext?.visibility = View.GONE
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, RC_PERMISSION)
            }
        })
    }

    private fun initData() {
        //Cek sudah login peduli lindungi belum, jika sudah lempar ke halaman selanjutnya
        if (viewModel?.repository?.getSharedPrefManager()?.isLoggedInPeli == true) {
            finish()
            startActivity(Intent(this, PeliScannerActivity::class.java))
        }
    }

    private fun setPrivacyPolicyText() {
        val registerText =
            SpannableString("Data di atas adalah milik saya sendiri. Saya juga menyetujui Syarat dan Ketentuan serta Kebijakan Privasi PeduliLindungi")

        val tos = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val url = "https://pedulilindungi.id/syarat-ketentuan/"
                val intent = Intent(this@PeliActivity, WebviewActivity::class.java)
                intent.putExtra("URL_TITLE", "Syarat dan Ketentuan")
                intent.putExtra("URL", url)
                startActivity(intent)
            }
        }
        registerText.setSpan(tos, 61, 81, 0)
        registerText.setSpan(ForegroundColorSpan(resources.getColor(R.color.brand2)), 61, 81, 0)

        val pp = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val url = "https://pedulilindungi.id/kebijakan-privasi-data/"
                val intent = Intent(this@PeliActivity, WebviewActivity::class.java)
                intent.putExtra("URL_TITLE", "Kebijakan Privasi")
                intent.putExtra("URL", url)
                startActivity(intent)

            }
        }
        registerText.setSpan(pp, 88, 105, 0)
        registerText.setSpan(ForegroundColorSpan(resources.getColor(R.color.brand2)), 88, 105, 0)
        binding?.apTvPrivacyPolicy?.movementMethod = LinkMovementMethod.getInstance();
        binding?.apTvPrivacyPolicy?.setText(registerText, TextView.BufferType.SPANNABLE)
        binding?.apTvPrivacyPolicy?.isSelected = true
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.apBtnNext -> {
                if (!validation()) return
                viewModel?.startToLoginPeli(
                    binding?.apMactNik?.text.toString(),
                    binding?.apMactName?.text.toString()
                )
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.apTilName?.isNotEmpty() == false) return false
        if (binding?.apTilNik?.isNotEmpty() == false) return false
        if (binding?.apTilNik?.isLengthLess(16) == false) return false
        if (binding?.apTvPrivacyPolicy?.isChecked == false) {
            showToast("Setujui syarat dan ketentuan terlebih dahulu")
            return false
        }
        return true
    }
}