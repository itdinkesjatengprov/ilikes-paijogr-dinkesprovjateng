package go.id.dinkesjatengprov.ilikes.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegister1Binding
import go.id.dinkesjatengprov.ilikes.helper.MIN_NIk_DIGIT
import go.id.dinkesjatengprov.ilikes.helper.MIN_PHONE_DIGIT
import go.id.dinkesjatengprov.ilikes.helper.ui.*
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage

class Register1Activity : BaseActivity<ActivityRegister1Binding, RegisterViewModel>(),
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Setting Action Bat
        supportActionBar?.title = "Buat Akun Baru"
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
        setupObserver()

        binding?.regTilNik?.textWatcherLengthless("NIK kurang dari 16 digit", 16)
        binding?.regTilPhone?.textWatcherLengthless("No. HP minimal 10 digit", 10)

        binding?.regBtnGoogle?.setOnClickListener(this)
        binding?.regBtnRegister?.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel?.register?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    //TODO Jika fitur pendaftaran layanan kesehatan sudah aktif
                    // arahkan ke halaman pengisian data diri terlebih dahulu
                    // val intent = Intent(this, Register2Activity::class.java)

                    finish()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Membuat Akun")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.regBtnGoogle -> showToastBuilding()
            binding?.regBtnRegister -> {
                if (!validation()) {
                    return
                }
                viewModel?.register(
                    binding?.regMactNik?.text.toString(),
                    binding?.regMactPhone?.text.toString(),
                    binding?.regMactName?.text.toString(),
                    binding?.regMactPassword?.text.toString()
                )
            }
        }
    }

    private fun validation(): Boolean {
        if (binding?.regTilNik?.isNotEmpty() == false) return false
        if (binding?.regTilNik?.isLengthLess(MIN_NIk_DIGIT) == false) return false
        if (binding?.regTilPhone?.isNotEmpty() == false) return false
        if (binding?.regTilPhone?.isLengthLess(MIN_PHONE_DIGIT) == false) return false
        if (binding?.regTilName?.isNotEmpty() == false) return false
        if (binding?.regTilPassword?.isNotEmpty() == false) return false
        if (binding?.regTilPasswordConfirmation?.isNotEmpty() == false) return false
        if (binding?.regTilPasswordConfirmation?.editText?.text.toString() != binding?.regTilPassword?.editText?.text.toString()) {
            binding?.regTilPasswordConfirmation?.error = "Konfirmasi password tidak sesuai"
            binding?.regTilPasswordConfirmation?.errorIconDrawable = null
            binding?.regTilPasswordConfirmation?.requestFocus()
            return false
        }
        return true
    }
}