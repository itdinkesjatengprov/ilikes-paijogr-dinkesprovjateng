package go.id.dinkesjatengprov.ilikes.ui.fragment.service

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.ServiceViewPagerAdapter
import go.id.dinkesjatengprov.ilikes.databinding.FragmentServiceBinding
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.registerService.RegisterService1Activity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseFragment
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class ServiceFragment : BaseFragment<FragmentServiceBinding, ServiceViewModel>(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceBinding.inflate(layoutInflater, container, false)
        (requireActivity() as HomeActivity).title = "Layanan Kesehatan"

        viewModel = ViewModelProvider(this, viewModelFactory)[ServiceViewModel::class.java]
        setupObserver()

        if (viewModel?.isLoggedIn()==true){
            binding?.fsViewpager?.adapter = ServiceViewPagerAdapter(this)
            TabLayoutMediator(
                binding?.fsTablayout!!,
                binding?.fsViewpager!!,
                true,
                true
            ) { tab, position ->
                if (position == 0) tab.text = "Jadwal" else tab.text = "Riwayat"
            }.attach()
        }else{
            openDialogLogin()
        }

        binding?.fsBtnRegister?.setOnClickListener(this)

        return binding?.root
    }

    private fun setupObserver() {

    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.fsBtnRegister -> {
                startActivity(Intent(requireContext(), RegisterService1Activity::class.java))
            }
        }
    }
}