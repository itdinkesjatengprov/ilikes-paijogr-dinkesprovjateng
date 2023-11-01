package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.LicenseDocumentAdapter
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityDocumentNewStrttkBinding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument.DocumentUploadActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument.DocumentUploadPdfActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener

class DocumentNewStrttkActivity :
    BaseActivity<ActivityDocumentNewStrttkBinding, StrttkViewModel>() {

    var license: LicenseModel? = null

    var licenseDocumentAdapter: LicenseDocumentAdapter? = null
    lateinit var licenseDocumentListener: LicenseDocumentAdapter.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentNewStrttkBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(StrttkViewModel::class.java)
        setupObserver()

        license = intent.getParcelableExtra(LICENSE)

        setLicenseDocumentListener()
        getRequirementDocument()

        binding?.adnsBtnSend?.setOnClickListener {
            viewModel?.updateStrttk(
                license?.copy(status = "Submitted")
            )
        }
    }

    /**
     * Mengatur aksi ketika user memilih akan mengupload dokumen
     * jika tipe syarat dokumen 'application/pdf' menuju ke DocumentUploadPdfActivity
     * jika tipe syarat dokumen 'application/png' menuju ke DocumentUploadActivity
     *
     * */
    private fun setLicenseDocumentListener() {
        licenseDocumentListener = object : LicenseDocumentAdapter.Listener {
            override fun onClick(status: String?, document: LicenseDocumentModel?) {
                val intent: Intent = if (document?.document?.type == "application/pdf") {
                    Intent(this@DocumentNewStrttkActivity, DocumentUploadPdfActivity::class.java)
                } else {
                    Intent(this@DocumentNewStrttkActivity, DocumentUploadActivity::class.java)
                }
                document?.licenseId = license?.id
                intent.putExtra(LICENSE_DOCUMENT, document)
                intent.putExtra(LICENSE_TYPE, STRTTK)
                intent.putExtra(LICENSE_STATUS, status)
                startActivityForResult(intent, RC_UPLOAD_FILE)
            }
        }
    }

    /**
     * Mendapatkan dokumen persyaratan berdasarkan tipe pengajuan
     * (Baru, diperpanjang atau yang lainnya)
     *
     * Menuju halaman ini terdapat 2 cara :
     * - FormSTRTTKActivity : tipe dokumen didapat dari intent dengan tag LICENSE_TYPE
     * - MainSTRTTKActivity : tipe dokumen didapat dari dalam data license
     * */
    private fun getRequirementDocument() {
        val documentType : String? =
            if (license?.documentType != null) license?.documentType else intent.getParcelableExtra<SelectionModel>(
                LICENSE_TYPE
            )?.id
        viewModel?.getRequirement(documentType)
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
                                viewModel?.getRequirement(license?.documentType)
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
        viewModel?.newStrttk?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Pengajuan STRTTK Berhasil")
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
                        .setTitle("Pengajuan STRTTK Gagal")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    private fun setButton() {
        //Karena user hanya dapat mengubah data saat status pengajuan DRAFT atau RETURNED
        //Tampilkan tombol jika status pengajuan DRAFT atau RETURNED
        if (license?.status?.toUpperCase() == "DRAFT" || license?.status?.toUpperCase() == "RETURNED") {
            binding?.adnsBtnSend?.visibility = View.VISIBLE
            var isButtonEnable = true
            //Lakukan perulangan untuk mengecek persyaratan yang telah diupload
            for (i in licenseDocumentAdapter?.list ?: arrayListOf()) {
                //Jika ada id yang masih null atau ada syarat yang masih berstatus rejected,
                // maka non-aktifkan tombol kirim
                if (i.id == null || i.status == "REJECTED") {
                    isButtonEnable = false
                }
            }
            binding?.adnsBtnSend?.isEnabled = isButtonEnable
            binding?.adnsBtnSend?.backgroundTintList =
                resources.getColorStateList(R.color.selector_button)
        } else {
            binding?.adnsBtnSend?.visibility = View.GONE
        }
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