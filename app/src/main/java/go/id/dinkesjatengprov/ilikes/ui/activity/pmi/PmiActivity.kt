package go.id.dinkesjatengprov.ilikes.ui.activity.pmi

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.PmiContactAdapter
import go.id.dinkesjatengprov.ilikes.adapter.PmiDonorAdapter
import go.id.dinkesjatengprov.ilikes.adapter.PmiStockAdapter
import go.id.dinkesjatengprov.ilikes.data.remote.model.FailureModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityPmiBinding
import go.id.dinkesjatengprov.ilikes.helper.MENU_PMI_BLOOD_DONOR
import go.id.dinkesjatengprov.ilikes.helper.MENU_PMI_BLOOD_STOCK
import go.id.dinkesjatengprov.ilikes.helper.MENU_PMI_CONTACT
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class PmiActivity : BaseActivity<ActivityPmiBinding, PmiViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(PmiViewModel::class.java)
        setupObserver()

        binding?.pmiRv?.layoutManager = LinearLayoutManager(this)

        val slug = intent.getIntExtra("SLUG", -1)
        when (slug) {
            MENU_PMI_CONTACT -> viewModel?.getContact()
            MENU_PMI_BLOOD_STOCK -> viewModel?.getStock()
            MENU_PMI_BLOOD_DONOR -> viewModel?.getDonor()
            else -> {
                DialogMessage(this)
                    .setTitle("Terjadi Kesalahan")
                    .setMessage("Slug tidak dikenali")
                    .setTextButtonPrimary("Kembali")
                    .setListenerButtonPrimary(object : DialogMessageListener {
                        override fun onClick(dialogMessage: DialogMessage) {
                            dialogMessage.dismiss()
                            finish()
                        }
                    })
                    .setDialogCancelable(false)
                    .showDialog()
            }
        }
    }

    private fun setupObserver() {
        viewModel?.contact?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.pmiRv?.adapter = PmiContactAdapter(it.data)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    setDialogFailure(it.failureModel, viewModel?.getContact())
                }
            }
        }
        viewModel?.stock?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.pmiRv?.adapter = PmiStockAdapter(it.data)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    setDialogFailure(it.failureModel, viewModel?.getStock())
                }
            }
        }
        viewModel?.donor?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    binding?.pmiRv?.adapter = PmiDonorAdapter(it.data)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    setDialogFailure(it.failureModel, viewModel?.getDonor())
                }
            }
        }
    }

    private fun setDialogFailure(failureModel: FailureModel?, function: Unit?) {
        DialogMessage(this)
            .setTitle("Terjadi Kesalahan")
            .setMessage(failureModel?.msgShow)
            .setTextButtonPrimary("Ulangi")
            .setTextButtonSecondary("Kembali")
            .setListenerButtonPrimary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    function!!
                }
            })
            .setListenerButtonSecondary(object : DialogMessageListener {
                override fun onClick(dialogMessage: DialogMessage) {
                    dialogMessage.dismiss()
                    finish()
                }
            })
            .setDialogCancelable(false)
            .showDialog()
    }
}