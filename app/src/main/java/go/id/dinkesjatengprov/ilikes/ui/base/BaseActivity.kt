package go.id.dinkesjatengprov.ilikes.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import go.id.dinkesjatengprov.ilikes.BuildConfig
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.viewmodel.ViewModelFactory
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogLoading
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener


abstract class BaseActivity<V, VM> : AppCompatActivity() {

    var binding: V? = null
    var viewModel: VM? = null

    lateinit var repository: MainRepository
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var loading: DialogLoading

    val versionName = BuildConfig.VERSION_NAME
    lateinit var appId : String
    var helper_confirmation_backpressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = MainRepository(this)
        viewModelFactory = ViewModelFactory(application, repository)

        loading = DialogLoading(this)

        appId = BuildConfig.APPLICATION_ID+".provider"
    }

    fun enableHomeButton() {
        supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_back))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun openDialogConfirmationBack(){
        DialogMessage(this)
            .setTitle("Perhatian")
            .setMessage("Data yang sudah dimasukkan akan hilang. Yakin ingin meninggalkan halaman ini?")
            .setTextButtonPrimary("Ya")
            .setTextButtonSecondary("Batal")
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    helper_confirmation_backpressed = true
                    onBackPressed()
                }
            }).showDialog()
    }

    fun showLoading(){
        loading = DialogLoading(this)
        loading.setCancelable(false)
        loading.show()
    }

    fun hideLoading(){
        if(loading.isShowing){
            loading.dismiss()
        }
    }

}