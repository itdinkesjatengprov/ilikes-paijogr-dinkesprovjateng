package go.id.dinkesjatengprov.ilikes.ui.fragment.profil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.google.android.material.tabs.TabLayoutMediator
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.ServiceViewPagerAdapter
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.DialogMessageBinding
import go.id.dinkesjatengprov.ilikes.databinding.FragmentHomeBinding
import go.id.dinkesjatengprov.ilikes.databinding.FragmentProfilBinding
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseFragment
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.fragment.home.HomeViewModel


class ProfilFragment : BaseFragment<FragmentProfilBinding, ProfilViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater, container, false)
        (requireActivity() as HomeActivity).title = "Profil"

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfilViewModel::class.java)
        setupObserver()

        if (viewModel?.isLoggedIn() == true) {
            viewModel?.getAuth()
        } else {
            openDialogLogin()
        }

        binding?.profilBtnLogout?.setOnClickListener {
            viewModel?.logout()
            requireActivity().finish()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return binding?.root
    }

    private fun setupObserver() {
        viewModel?.auth?.observe(viewLifecycleOwner) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.fpTvNik?.text = it.data?.nik ?: "-"
                    binding?.fpTvName?.text = it.data?.name ?: "-"
                    binding?.fpTvPhone?.text = it.data?.phone ?: "-"
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(requireContext())
                        .setTitle("Gagal mendapatkan data")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }
    }
}