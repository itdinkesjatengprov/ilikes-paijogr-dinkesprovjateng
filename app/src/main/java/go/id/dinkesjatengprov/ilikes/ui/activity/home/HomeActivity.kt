package go.id.dinkesjatengprov.ilikes.ui.activity.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.databinding.ActivityHomeBinding
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import me.ibrahimsn.lib.OnItemSelectedListener

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.home_fragment)
        binding?.homeBottomnav?.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_notification -> showToast("Sedang dalam pengembangan")
//            R.id.menu_customer_service -> showToast("Sedang dalam pengembangan")
        }
        return super.onOptionsItemSelected(item)
    }


}