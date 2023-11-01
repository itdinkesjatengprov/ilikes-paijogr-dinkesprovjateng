package go.id.dinkesjatengprov.ilikes.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.data.viewmodel.ViewModelFactory
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogLoading
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

abstract class BaseFragment<A, V> : Fragment() {

    var viewModel: V? = null
    var binding: A? = null

    lateinit var repository: MainRepository
    lateinit var viewModelFactory: ViewModelFactory

    var loading: DialogLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = MainRepository(requireContext())
        viewModelFactory = ViewModelFactory(activity?.application!!, repository)
    }

    fun showLoading() {
        hideLoading()
        loading = DialogLoading(requireContext())
        loading?.setCancelable(false)
        loading?.show()
    }

    fun hideLoading() {
        if (loading != null && loading?.isShowing == true) loading?.dismiss()
    }

    fun openDialogLogin(){
        DialogMessage(requireContext())
            .setTitle("Anda belum login")
            .setMessage("Silahkan login terlbih dahulu untuk mengakses Layanan Kesehatan")
            .setTextButtonPrimary("Login")
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    backToLogin()
                }
            })
            .setTextButtonSecondary("Nanti")
            .setListenerButtonSecondary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    requireActivity().onBackPressed()
                }
            })
            .setDialogCancelable(false)
            .showDialog()
    }

    private fun backToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}