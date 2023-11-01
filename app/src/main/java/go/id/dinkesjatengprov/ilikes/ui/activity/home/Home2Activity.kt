package go.id.dinkesjatengprov.ilikes.ui.activity.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.MenuAdapter
import go.id.dinkesjatengprov.ilikes.data.menuDashboard2
import go.id.dinkesjatengprov.ilikes.data.menuInformation
import go.id.dinkesjatengprov.ilikes.data.menuLayananPengaduan
import go.id.dinkesjatengprov.ilikes.data.menuMyDinkes
import go.id.dinkesjatengprov.ilikes.databinding.ActivityHome2Binding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class Home2Activity : BaseActivity<ActivityHome2Binding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "PAIJO"
        enableHomeButton()

        binding?.homeRvDinkes?.layoutManager = GridLayoutManager(this, 3)
        binding?.homeRvDinkes?.adapter = MenuAdapter(menuMyDinkes)

        binding?.homeRvHealth?.layoutManager = GridLayoutManager(this, 3)
        binding?.homeRvHealth?.adapter = MenuAdapter(menuDashboard2)

        binding?.homeRvInformation?.layoutManager = GridLayoutManager(this, 3)
        binding?.homeRvInformation?.adapter = MenuAdapter(menuInformation)

        binding?.homeRvComplaintment?.layoutManager = GridLayoutManager(this, 3)
        binding?.homeRvComplaintment?.adapter = MenuAdapter(menuLayananPengaduan)
    }
}

