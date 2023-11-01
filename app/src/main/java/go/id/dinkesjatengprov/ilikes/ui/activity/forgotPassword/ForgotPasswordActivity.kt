package go.id.dinkesjatengprov.ilikes.ui.activity.forgotPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityForgotPasswordBinding
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegister1Binding
import go.id.dinkesjatengprov.ilikes.helper.ui.textWatcherLengthless
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.register.RegisterViewModel
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Lupa Kata Sandi"
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(ForgotPasswordViewModel::class.java)
        setupObserver()

        binding?.loginTilPhone?.textWatcherLengthless("No. HP kurang dari 10 digit", 10)

        binding?.loginBtnForgotPassword?.setOnClickListener {
            viewModel?.checkPassword(binding?.loginTilPhone?.editText?.text.toString())
        }

        binding?.loginBtnForgotPassword2?.setOnClickListener {
            viewModel?.resetPassword(
                binding?.loginTilPhone?.editText?.text.toString(),
                binding?.loginTilPassword?.editText?.text.toString(),
                binding?.loginTilRePassword?.editText?.text.toString(),
            )
        }
    }

    private fun setupObserver() {
        viewModel?.checkPassword?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.loginTilPhone?.isEnabled = false
                    binding?.loginBtnForgotPassword?.visibility = View.GONE
                    binding?.loginGroup?.visibility = View.VISIBLE
                }
                StatusRequest.ERROR -> {
                    hideLoading()

                    DialogMessage(this)
                        .setTitle("Gagal Mengecek Nomer Telepon")
                        .setMessage("${it.failureModel?.msgShow}")
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }

        viewModel?.resetPassword?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Berhasil")
                        .setMessage("Berhasil mengubah kata sandi")
                        .setTextButtonPrimary("Login")
                        .setDialogCancelable(false)
                        .setListenerButtonPrimary(object : DialogMessageListener{
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                finishAffinity()
                                val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        })
                        .showDialog()
                }
                StatusRequest.ERROR -> {
                    hideLoading()

                    DialogMessage(this)
                        .setTitle("Gagal Mengecek Nomer Telepon")
                        .setMessage("${it.failureModel?.msgShow}")
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }
}