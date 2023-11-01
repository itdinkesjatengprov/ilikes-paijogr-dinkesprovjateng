package go.id.dinkesjatengprov.ilikes.ui.fragment.license

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseBackground
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseColor
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseText
import go.id.dinkesjatengprov.ilikes.databinding.FragmentLicenseBinding
import go.id.dinkesjatengprov.ilikes.helper.STRTTK
import go.id.dinkesjatengprov.ilikes.helper.TUBEL
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk.MainStrttkActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel.MainTubelActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.WebviewActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseFragment
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class LicenseFragment : BaseFragment<FragmentLicenseBinding, LicenseViewModel>(),
    View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLicenseBinding.inflate(layoutInflater, container, false)
        (requireActivity() as HomeActivity).title = "Perijinan"

        viewModel = ViewModelProvider(this, viewModelFactory)[LicenseViewModel::class.java]
        setupObserver()

        if (viewModel?.isLoggedIn() == true) {
            viewModel?.getActiveSTRTTK()
        } else {
            openDialogLogin()
        }

        binding?.licenseTvRsNew?.setOnClickListener(this)
        binding?.licenseTvRsPerpanjangan?.setOnClickListener(this)
        binding?.licenseTvRsPerubahan?.setOnClickListener(this)
        binding?.licenseTvLabkesNew?.setOnClickListener(this)
        binding?.licenseTvLabkesPerpanjangan?.setOnClickListener(this)
        binding?.licenseTvLabkesPerubahan?.setOnClickListener(this)


        return binding?.root
    }

    fun openDialogUnderDevelopment(){
        DialogMessage(requireContext())
            .setTitle("Maaf, fitur ini sedang dalam pengembangan")
            .setMessage("Tunggu update selanjutnya untuk dapat menggunakan fitur ini")
            .setTextButtonPrimary("Oke")
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    requireActivity().onBackPressed()
                }
            })
            .setDialogCancelable(false)
            .showDialog()
    }

    private fun setupObserver() {
        viewModel?.strttkActive?.observe(viewLifecycleOwner) {
            when (it.statusRequest) {
                StatusRequest.SUCCESS -> {
                    //Active Card Click Listener
                    binding?.licenseCvStrttk?.setOnClickListener(this)

                    //todo Get Data Tubel
                    //viewModel?.getActiveTubel()

                    //Set Data STRTTK to View
                    initViewSTRTTK(it.data)
                }
                StatusRequest.EMPTY -> {
                    binding?.licenseCvStrttk?.setOnClickListener(this)
                    initViewSTRTTK(null)
                }
                StatusRequest.ERROR -> {
                    openDialogError("STRTTK", it.failureModel)
                }
            }
        }
        viewModel?.tubelActive?.observe(viewLifecycleOwner) {
            when (it.statusRequest) {
                StatusRequest.SUCCESS -> {
                    //Active Card Click Listener
                    binding?.licenseCvTubel?.setOnClickListener(this)

                    initViewTubel(it.data)
                }
                StatusRequest.ERROR -> {
                    openDialogError("TUBEL", it.failureModel)
                }
            }
        }
    }

    private fun initViewTubel(data: LicenseModel?) {
        binding?.licensePbTubel?.visibility = View.GONE
        if (data != null) {
            binding?.licenseTvTubelId?.visibility = View.VISIBLE
            binding?.licenseTvTubelId?.text = data.id?.toUpperCase()
            binding?.licenseTvTubelStatus?.text = "Aktif"
            binding?.licenseTvTubelStatus?.setTextColor(
                requireContext().resources.getColor(
                    R.color.pure_white
                )
            )
            binding?.licenseTvTubelStatus?.background =
                requireContext().resources.getDrawable(R.drawable.bg_license_status_active)
            binding?.licenseTvTubelProcess?.visibility = View.VISIBLE
            binding?.licenseTvTubelProcess?.text = setStatusLicenseText(data.status)
            binding?.licenseTvTubelProcess?.background =
                requireContext().setStatusLicenseBackground(data.status)
            binding?.licenseTvTubelProcess?.setTextColor(
                requireContext().setStatusLicenseColor(
                    data.status
                )
            )
        } else {
            binding?.licenseTvTubelId?.visibility = View.INVISIBLE
            binding?.licenseTvTubelStatus?.text = "Belum Ada"
            binding?.licenseTvTubelStatus?.background =
                requireContext().getDrawable(R.drawable.bg_license_status_nothing)
            binding?.licenseTvTubelProcess?.visibility = View.INVISIBLE
        }
    }

    private fun initViewSTRTTK(data: LicenseModel?) {
        binding?.licensePbStrttk?.visibility = View.GONE
        if (data != null) {
            binding?.licenseTvStrttkId?.visibility = View.VISIBLE
            binding?.licenseTvStrttkId?.text = data.id?.toUpperCase()
            binding?.licenseTvStrttkStatus?.text = "Aktif"
            binding?.licenseTvStrttkStatus?.setTextColor(
                requireContext().resources.getColor(
                    R.color.pure_white
                )
            )
            binding?.licenseTvStrttkStatus?.background =
                requireContext().resources.getDrawable(R.drawable.bg_license_status_active)
            binding?.licenseTvStrttkProcess?.visibility = View.VISIBLE
            binding?.licenseTvStrttkProcess?.text = setStatusLicenseText(data.status)
            binding?.licenseTvStrttkProcess?.background =
                requireContext().setStatusLicenseBackground(data.status)
            binding?.licenseTvStrttkProcess?.setTextColor(
                requireContext().setStatusLicenseColor(
                    data.status
                )
            )
        } else {
            binding?.licenseTvStrttkId?.visibility = View.INVISIBLE
            binding?.licenseTvStrttkStatus?.text = "Belum Ada"
            binding?.licenseTvStrttkStatus?.background =
                requireContext().getDrawable(R.drawable.bg_license_status_nothing)
            binding?.licenseTvStrttkProcess?.visibility = View.INVISIBLE
        }
    }

    private fun openDialogError(kode: String, failureModel: FailureModel?) {
        DialogMessage(requireActivity())
            .setTitle("Gagal Mendapatkan Data $kode")
            .setMessage(failureModel?.msgShow)
            .setTextButtonPrimary("Ulangi")
            .setTextButtonSecondary("Batal")
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    if (kode == "STRTTK") {
                        viewModel?.getActiveSTRTTK()
                    } else {
                        viewModel?.getActiveTubel()
                    }
                }
            })
            .setListenerButtonSecondary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    requireActivity().onBackPressed()
                }
            })
            .setDialogCancelable(false)
            .showDialog()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.licenseCvStrttk -> {
                val intent = Intent(requireActivity(), MainStrttkActivity::class.java)
                intent.putExtra(STRTTK, viewModel?.strttkActive?.value?.data)
                startActivity(intent)
            }
            binding?.licenseCvTubel -> {
                val intent = Intent(requireActivity(), MainTubelActivity::class.java)
                intent.putExtra(TUBEL, viewModel?.tubelActive?.value?.data)
                startActivity(intent)
            }
            binding?.licenseTvRsNew -> {
                val url = "https://docs.google.com/forms/d/e/1FAIpQLSdl5PvGY-jPMwulGfwiDUSIkpfINNYzz81JdLaGnPmiSEvrCQ/viewform"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
            binding?.licenseTvRsPerpanjangan -> {
                val url = "https://bit.ly/IjinPerpanjanganRS"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
            binding?.licenseTvRsPerubahan -> {
                val url = "https://bit.ly/IjinPerubahanRS"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
            binding?.licenseTvLabkesNew -> {
                val url = "https://s.id/IzinLabBaru"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
            binding?.licenseTvLabkesPerpanjangan -> {
                val url = "https://s.id/IzinPerpanjanganLab"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
            binding?.licenseTvLabkesPerubahan -> {
                val url = "https://s.id/IzinLabPerubahan"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(url))
                startActivity(intent)
            }
        }
    }
}