package go.id.dinkesjatengprov.ilikes.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityLoginBinding
import go.id.dinkesjatengprov.ilikes.helper.ui.*
import go.id.dinkesjatengprov.ilikes.ui.activity.forgotPassword.ForgotPasswordActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.register.Register1Activity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        setupObserver()

        //Init Some View
        setSpannableRegisterText()

        binding?.loginTilPhone?.textWatcherLengthless("No. HP kurang dari 10 digit", 10)

        binding?.loginTvSkip?.setOnClickListener(this)
        binding?.loginBtnGoogle?.setOnClickListener(this)
        binding?.loginBtnLogin?.setOnClickListener(this)
        binding?.loginTvForgotPassword?.setOnClickListener(this)
        binding?.loginTvRegister?.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel?.account?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                StatusRequest.ERROR -> {
                    hideLoading()

                    DialogMessage(this)
                        .setTitle("Gagal Login")
                        .setMessage("${it.failureModel?.msgShow}")
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    /**
     * Fungsi ini mengatur text pendaftaran agar memiliki 2 warna
     *
     * */
    private fun setSpannableRegisterText() {
        val registerText = SpannableString("Belum punya akun? Buat Akun")
        registerText.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.brand2)),
            17, registerText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding?.loginTvRegister?.text = registerText
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.loginTvSkip -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            binding?.loginBtnGoogle -> showToastBuilding()
            binding?.loginBtnLogin -> {
                if (!validation()) {
                    return
                }
                viewModel?.login(
                    binding?.loginMactPhone?.text.toString(),
                    binding?.loginMactPassword?.text.toString()
                )
            }
            binding?.loginTvForgotPassword -> startActivity(Intent(this, ForgotPasswordActivity::class.java))
            binding?.loginTvRegister -> startActivity(Intent(this, Register1Activity::class.java))
        }

    }

    /**
     * Fungsi ini akan melakukan validasi terhadap apa yang diinputkan oleh user
     *
     * @return boolean
     * */
    private fun validation(): Boolean {
        if (binding?.loginTilPhone?.isNotEmpty() == false) return false
        if (binding?.loginTilPhone?.isLengthLess(10) == false) return false
        if (binding?.loginTilPassword?.isNotEmpty() == false) return false
        return true
    }
}