package go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.data.model.LicenseDocumentModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityDocumentUploadBinding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.parser.createImageFile
import go.id.dinkesjatengprov.ilikes.helper.parser.getRealPathFromUri
import go.id.dinkesjatengprov.ilikes.helper.parser.writeResponseBodyToDisk
import go.id.dinkesjatengprov.ilikes.helper.ui.openCamera
import go.id.dinkesjatengprov.ilikes.helper.ui.openGallery
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class DocumentUploadActivity :
    BaseActivity<ActivityDocumentUploadBinding, DocumentUploadViewModel>(),
    View.OnClickListener {

    var document: LicenseDocumentModel? = null
    var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentUploadBinding.inflate(layoutInflater)
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

        binding?.aduBtnCamera?.setOnClickListener(this)
        binding?.aduBtnGallery?.setOnClickListener(this)
        binding?.aduBtnUpload?.setOnClickListener(this)
    }

    /**
     * MENGATUR UNTUK MENAMPILKAN / MENYEMBUNYIKAN TOMBOL UPLOAD DAN PILIH DOKUMEN
     *
     * @param status status pengajuan dokumen yang menentukan tombol disembunyikan / ditampilkan
     *
     * */
    private fun setVisibilityButton(status: String?) {
        /**
         * CEK JIKA STATUS PENGAJUAN DRAFT / RETURNED,
         * TAMPILKAN TOMBOL UNTUK UPLOAD DOKUMEN
         *
         * */
        if (status?.toUpperCase() == "DRAFT" || status?.toUpperCase() == "RETURNED") {
            /**
             * JIKA STATUS PENGAJUAN DRAFT / RETURNED, NAMUN STATUS DOKUMEN REJECTED
             * TAMPILKAN TOMBOL UNTUK UPLOAD DOKUMEN
             *
             * */
            if (document?.status != "APPROVED") {
                binding?.aduBtnCamera?.visibility = View.VISIBLE
                binding?.aduBtnGallery?.visibility = View.VISIBLE
                binding?.aduBtnUpload?.visibility = View.VISIBLE
            } else {
                binding?.aduBtnCamera?.visibility = View.GONE
                binding?.aduBtnGallery?.visibility = View.GONE
                binding?.aduBtnUpload?.visibility = View.GONE
            }
        } else {
            binding?.aduBtnCamera?.visibility = View.GONE
            binding?.aduBtnGallery?.visibility = View.GONE
            binding?.aduBtnUpload?.visibility = View.GONE
        }
    }

    private fun loadFile() {
        val tempFile =
            File(getExternalFilesDir(null).toString() + File.separator.toString() + "${document?.link}")
        if (tempFile.exists()) {
            fileImage = tempFile
            showImageFile()
        } else {
            viewModel?.downloadFile(document?.link)
        }
    }

    private fun showImageFile() {
        Glide.with(this)
            .load(fileImage)
            .into(binding?.aduIv!!)
    }

    private fun checkAppPermission() {
        helperRequestPermission(object : HelperPermissionListener {
            override fun allPermissionGranted() {
                binding?.aduBtnCamera?.visibility = View.VISIBLE
                binding?.aduBtnGallery?.visibility = View.VISIBLE
            }

            override fun anyPermissionDenied() {
                Toast.makeText(this@DocumentUploadActivity, "anypermissiondenied1", Toast.LENGTH_SHORT).show()
                checkAppPermission()
            }

            override fun anyPermissionPermantentlyDenied() {
                Toast.makeText(this@DocumentUploadActivity, "anypermissiondenied2", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, RC_PERMISSION)
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
                        .setTitle("Gegal menampilkan gambar")
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
                        .setTitle("Gagal upload gambar")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
        viewModel?.uploadDocumentTubel?.observe(this) {
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
                        .setTitle("Gagal upload gambar")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .showDialog()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.aduBtnCamera -> {
                fileImage = createImageFile()
                openCamera(fileImage, RC_CAMERA, appId)
            }
            binding?.aduBtnGallery -> {
                openGallery(RC_GALLERY)
            }
            binding?.aduBtnUpload -> {
                if (fileImage == null) {
                    showToast("Pilih file terlebih dahulu")
                    return
                }
                //Cek Tipe dokumen yang akan diupload untuk syarat STRTTK atau Tubel
                val licenseType = intent.getStringExtra(LICENSE_TYPE)
                if (licenseType == STRTTK) {
                    uploadSTRTTK()
                } else if (licenseType == TUBEL) {
                    uploadTubel()
                }
            }
        }
    }

    private fun uploadTubel() {
        if (document?.id == null) {
            viewModel?.postDocumentTubel(
                document?.licenseId,
                document?.document?.id,
                fileImage
            )
        } else {
            viewModel?.updateDocumentTubel(document?.id, fileImage)
        }
    }

    private fun uploadSTRTTK() {
        Log.d("D0CUMENT", "uploadSTRTTK: ${document?.id}")
        if (document?.id == null || document?.status?.toUpperCase() != "REJECTED") {
            showToast("UPLOAD BARU")
            viewModel?.postDocumentSTRTTK(
                document?.document?.type,
                document?.licenseId,
                document?.document?.id,
                fileImage
            )
        } else {
            showToast("UPDATE DOKUMEN")
            viewModel?.updateDocumentSTRTTK(document?.document?.type, document?.id, fileImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        setImage()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            "Gagal mendapatkan gambar. ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            RC_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val path = getRealPathFromUri(data?.data)
                        path?.let {
                            fileImage = File(path)
                            setImage()
                        }
                    } catch (e: IOException) {
                        Toast.makeText(
                            this,
                            "Gagal mendapatkan gambar. ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setImage() {
        lifecycleScope.launch {
            fileImage?.let {
                fileImage = Compressor.compress(this@DocumentUploadActivity, it)
                Glide.with(this@DocumentUploadActivity)
                    .load(fileImage)
                    .into(binding?.aduIv!!)
            }
        }
    }
}