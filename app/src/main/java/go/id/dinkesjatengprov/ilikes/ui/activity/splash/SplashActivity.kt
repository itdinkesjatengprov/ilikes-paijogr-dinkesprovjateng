package go.id.dinkesjatengprov.ilikes.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.databinding.ActivitySplashBinding
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.splashTvVersion?.text = "Versi $versionName"

        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]

        Handler().postDelayed({
            if (viewModel?.isLoggedIn() == true) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }, 3000)

    }
}