package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.BuildConfig
import go.id.dinkesjatengprov.ilikes.adapter.ResultLicenseAdapter
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseResultModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityResultStrttkBinding
import go.id.dinkesjatengprov.ilikes.helper.LICENSE
import go.id.dinkesjatengprov.ilikes.helper.parser.writeResponseBodyToDisk
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogResult
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogResultListener
import java.io.File


class ResultStrttkActivity : BaseActivity<ActivityResultStrttkBinding, StrttkViewModel>() {

    var license: LicenseModel? = null

    var tempResult: File? = null
    var tempLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultStrttkBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(StrttkViewModel::class.java)
        setupObserver()

        license = intent.getParcelableExtra(LICENSE)
        viewModel?.getResult(license?.id)
    }

    private fun setupObserver() {
        viewModel?.result?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()

                    val adapter = ResultLicenseAdapter(
                        it.data,
                        object : ResultLicenseAdapter.ResultLicenseListener {
                            override fun downloadResult(result: LicenseResultModel?) {
                                tempLink = result?.link
                                loadFile()
                            }
                        })
                    binding?.arsRv?.layoutManager = LinearLayoutManager(this)
                    binding?.arsRv?.adapter = adapter
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
                                viewModel?.getResult(license?.id)
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

        viewModel?.fileResult?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    writeResponseBodyToDisk(tempLink, it.data!!)
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
    }

    private fun loadFile() {
        val tempFile =
            File(getExternalFilesDir(null).toString() + File.separator.toString() + "$tempLink")
        if (tempFile.exists()) {
            tempResult = tempFile
            showTempResult()
        } else {
            viewModel?.downloadFile(tempLink)
        }
    }

    private fun showTempResult() {
        DialogResult(this, tempResult)
            .setListenerButtonPrimary(object : DialogResultListener {
                override fun onClick(dialogMessage: DialogResult) {
                    dialogMessage.dismiss()
                    val uri: Uri = uriFromFile(this@ResultStrttkActivity, tempResult!!)

                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                    shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    shareIntent.type = "application/pdf"
                    startActivity(Intent.createChooser(shareIntent, "share.."))
                }
            })
            .showDialog()
    }

    fun uriFromFile(context: Context, file:File):Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}