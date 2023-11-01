package go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.LicenseDocumentAdapter
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityDocumentNewTubelBinding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument.DocumentUploadActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class DocumentNewTubelActivity : BaseActivity<ActivityDocumentNewTubelBinding, TubelViewModel>() {

    var typeLicense: SelectionModel? = null
    var license: LicenseModel? = null

    var licenseDocumentAdapter: LicenseDocumentAdapter? = null
    lateinit var licenseDocumentListener: LicenseDocumentAdapter.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentNewTubelBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(TubelViewModel::class.java)
        setupObserver()

        typeLicense = intent.getParcelableExtra(LICENSE_TYPE)
        license = intent.getParcelableExtra(LICENSE)

        licenseDocumentListener = object : LicenseDocumentAdapter.Listener {
            override fun onClick(status: String?, document: LicenseDocumentModel?) {
                val intent =
                    Intent(this@DocumentNewTubelActivity, DocumentUploadActivity::class.java)
                intent.putExtra(LICENSE_DOCUMENT, document)
                intent.putExtra(LICENSE_TYPE, TUBEL)
                startActivityForResult(intent, RC_UPLOAD_FILE)
            }
        }

        //todo DELETE THIS WHEN SERVER IS READY
        val listRequirementDummy = createDummyList("dummyRequirement.json")
        viewModel?.getRequirement(typeLicense?.id, listRequirementDummy)

        binding?.adnsBtnSend?.setOnClickListener {
            viewModel?.updateTubel(
                license?.copy(status = "SUBMITTED")
            )
        }
    }

    private fun setupObserver() {
        viewModel?.requirement?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    binding?.adnsRv?.layoutManager = LinearLayoutManager(this)
                    licenseDocumentAdapter =
                        LicenseDocumentAdapter(license?.status, it.data, licenseDocumentListener)
                    binding?.adnsRv?.adapter = licenseDocumentAdapter

                    viewModel?.getDocument(license?.id)
                }
                StatusRequest.EMPTY -> hideLoading()
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal mendapatkan daftar syarat dokumen")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setTextButtonSecondary("Batal")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                //todo DELETE THIS WHEN SERVER IS READY
                                var listRequirementDummy = createDummyList("dummyRequirement.json")
                                viewModel?.getRequirement(typeLicense?.id, listRequirementDummy)
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
        }
        viewModel?.document?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    licenseDocumentAdapter =
                        LicenseDocumentAdapter(license?.status, it.data, licenseDocumentListener)
                    binding?.adnsRv?.adapter = licenseDocumentAdapter

                    setButton()
                }
                StatusRequest.EMPTY -> hideLoading()
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal mendapatkan daftar dokumen yang telah diupload")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setTextButtonSecondary("Batal")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                viewModel?.getDocument(license?.id)
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
        }
        viewModel?.tubelNew?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Pengajuan TUBEL Berhasil")
                        .setMessage("Mohon tunggu untuk proses selanjutnya")
                        .setTextButtonPrimary("Ok")
                        .setListenerButtonPrimary(object : DialogMessageListener {
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                finish()
                            }
                        })
                        .setDialogCancelable(false)
                        .showDialog()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Pengajuan TUBEL Gagal")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    private fun setButton() {
        var isButtonEnable = true
        for (i in licenseDocumentAdapter?.list ?: arrayListOf()) {
            if (i.id == null) {
                isButtonEnable = false
            }
        }
        binding?.adnsBtnSend?.isEnabled = isButtonEnable
        binding?.adnsBtnSend?.backgroundTintList =
            resources.getColorStateList(R.color.selector_button)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_UPLOAD_FILE -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel?.getDocument(license?.id)
                }
            }
        }
    }
}