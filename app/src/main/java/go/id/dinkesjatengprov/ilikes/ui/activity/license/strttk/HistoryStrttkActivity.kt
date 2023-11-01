package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.HistoryLicenseAdapter
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityHistoryStrttkBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class HistoryStrttkActivity : BaseActivity<ActivityHistoryStrttkBinding, StrttkViewModel>() {

    lateinit var adapter: HistoryLicenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryStrttkBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(StrttkViewModel::class.java)
        setupObserver()

        //init recyclerview
        adapter = HistoryLicenseAdapter(viewModel?.history?.value?.data ?: arrayListOf())
        binding?.ahsRv?.layoutManager = LinearLayoutManager(this)
        binding?.ahsRv?.adapter = adapter

        viewModel?.getHistory()

        binding?.ahsSrl?.setOnRefreshListener {
            binding?.ahsSrl?.isRefreshing = false
            viewModel?.getHistory()
        }
    }

    private fun setupObserver() {
        viewModel?.history?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    adapter.updateData(it.data)
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Terjadi Kesalahan")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setTextButtonSecondary("Batal")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                viewModel?.getHistory()
                            }
                        })
                        .setListenerButtonSecondary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                finish()
                            }
                        })
                        .showDialog()
                }
            }
        }
    }
}