package go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.provider.Settings
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityDocumentUploadPdfBinding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.parser.getFile
import go.id.dinkesjatengprov.ilikes.helper.parser.writeResponseBodyToDisk
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import java.io.File

class DocumentUploadPdfActivity :
    BaseActivity<ActivityDocumentUploadPdfBinding, DocumentUploadViewModel>(),
    View.OnClickListener {

    var document: LicenseDocumentModel? = null
    var pdfUri: Uri? = null
    var pdfFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentUploadPdfBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DocumentUploadViewModel::class.java)
        setupObserver()

        //Check App Permission
        checkAppPermission()

        document = intent.getParcelableExtra(LICENSE_DOCUMENT)

        val status = intent.getStringExtra(LICENSE_STATUS)
        setVisibilityButton(status)

        if (document?.link != null) {
            loadFile()
        }

        binding?.adupBtnSelectFile?.setOnClickListener(this)
        binding?.adupBtnUpload?.setOnClickListener(this)
    }

    private fun setVisibilityButton(status: String?) {
        /**
         * CEK JIKA STATUS PENGAJUAN DRAFT / RETURNED,
         * TAMPILKAN TOMBOL UNTUK UPLOAD DOKUMEN
         *
         * */
        if (status?.toUpperCase() == "DRAFT" || status?.toUpperCase() == "RETURNED" || status?.toUpperCase() == "REJECTED") {
            /**
             * JIKA STATUS PENGAJUAN DRAFT / RETURNED, NAMUN STATUS DOKUMEN REJECTED
             * TAMPILKAN TOMBOL UNTUK UPLOAD DOKUMEN
             *
             * */
            if (document?.status != "APPROVED") {
                binding?.adupBtnUpload?.visibility = View.VISIBLE
                binding?.adupBtnSelectFile?.visibility = View.VISIBLE
            } else {
                binding?.adupBtnUpload?.visibility = View.GONE
                binding?.adupBtnSelectFile?.visibility = View.GONE
            }
        } else {
            binding?.adupBtnUpload?.visibility = View.GONE
            binding?.adupBtnSelectFile?.visibility = View.GONE
        }
    }

    private fun checkAppPermission() {
        helperRequestPermission(object : HelperPermissionListener {
            override fun allPermissionGranted() {

            }

            override fun anyPermissionDenied() {
                checkAppPermission()
            }

            override fun anyPermissionPermantentlyDenied() {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(
                    intent,
                    RC_PERMISSION
                )
            }
        })
    }

    private fun setupObserver() {
        viewModel?.pdf?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    writeResponseBodyToDisk(document?.link, it.data!!)
                    loadFile()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gegal menampilkan PDF")
                        .setMessage(it.failureModel?.msgShow)
                        .show()
                }
            }
        }
        viewModel?.uploadDocumentSTRTTK?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    val intent = Intent()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal upload file")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    private fun uploadSTRTTK() {
        if (document?.id == null) {
            viewModel?.postDocumentSTRTTK(
                document?.document?.type,
                document?.licenseId,
                document?.document?.id,
                pdfFile
            )
        } else {
            viewModel?.updateDocumentSTRTTK(document?.document?.type, document?.id, pdfFile)
        }
    }

    private fun loadFile() {
        val tempFile =
            File(getExternalFilesDir(null).toString() + File.separator.toString() + "${document?.link}")
        if (tempFile.exists()) {
            pdfFile = tempFile
            showPdfFile()
        } else {
            viewModel?.downloadFile(document?.link)
        }
    }

    private fun showPdfFile() {
        binding?.adupPdfview?.fromFile(pdfFile!!)?.show()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.adupBtnSelectFile -> {
                val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                pdfIntent.type = "application/pdf"
                pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(pdfIntent, RC_PICK_PDF)
            }
            binding?.adupBtnUpload -> {
                if (pdfUri == null) {
                    showToast("Pilih file terlebih dahulu")
                    return
                }
                //Cek Tipe dokumen yang akan diupload untuk syarat STRTTK atau Tubel
                val licenseType = intent.getStringExtra(LICENSE_TYPE)
                if (licenseType == STRTTK) {
                    uploadSTRTTK()
                } else if (licenseType == TUBEL) {

                }
            }
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_PICK_PDF -> {
                if (resultCode == RESULT_OK) {
                    pdfUri = data?.data!!
                    val uri: Uri = data.data!!
                    val uriString: String = uri.toString()
                    var pdfName: String? = null
                    if (uriString.startsWith("content://")) {
                        var myCursor: Cursor? = null
                        try {
                            // Setting the PDF to the TextView
                            myCursor = applicationContext!!.contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null
                            )
                            if (myCursor != null && myCursor.moveToFirst()) {
                                pdfName =
                                    myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                                binding?.adupTvFilename?.text = pdfName

                                pdfFile = getFile(this, pdfUri!!)

                                showPdfFile()
                            }
                        } finally {
                            myCursor?.close()
                        }
                    }
                }
            }
        }
    }
}