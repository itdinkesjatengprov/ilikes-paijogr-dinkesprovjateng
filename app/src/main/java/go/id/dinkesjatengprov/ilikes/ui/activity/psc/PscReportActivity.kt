package go.id.dinkesjatengprov.ilikes.ui.activity.psc

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import go.id.dinkesjatengprov.ilikes.adapter.PscPhotoAdapter
import go.id.dinkesjatengprov.ilikes.data.model.psc.PostReportBodyModel
import go.id.dinkesjatengprov.ilikes.data.model.psc.PscLocationModel
import go.id.dinkesjatengprov.ilikes.data.model.psc.PscPhotoModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityPscReportBinding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.parser.createImageFile
import go.id.dinkesjatengprov.ilikes.helper.parser.isLowerContains
import go.id.dinkesjatengprov.ilikes.helper.parser.latlongToAddress
import go.id.dinkesjatengprov.ilikes.helper.ui.openCamera
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessageListener
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectCategory
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectCategoryListener
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File

class PscReportActivity : BaseActivity<ActivityPscReportBinding, PscViewModel>(),
    View.OnClickListener {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient   //Mendapatkan lokasi terakhir
    lateinit var locationRequest: LocationRequest                           //mengatur setelan lokasi
    lateinit var locationCallback: LocationCallback                         //Mendapatkan update lokasi

    //Variable needed
    var finalCategory: String? = null
    var finalLatitude: Double? = null
    var finalLongitude: Double? = null

    lateinit var photoAdapter: PscPhotoAdapter
    var tempFile: File? = null
    var finalPhoto: ArrayList<PscPhotoModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPscReportBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(PscViewModel::class.java)
        setupObserver()

        showDialogCategory()
        setupLocation()

        photoAdapter = PscPhotoAdapter(finalPhoto)
        binding?.aprRvPhoto?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.aprRvPhoto?.adapter = photoAdapter

        checkAppPermission()

        binding?.aprTvCategory?.setOnClickListener(this)
        binding?.aprIvGps?.setOnClickListener(this)
        binding?.aprLlPickPhoto?.setOnClickListener(this)
        binding?.aprBtnSend?.setOnClickListener(this)
        binding?.aprTvLogout?.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel?.report?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Berhasil")
                        .setMessage("Berhasil mengirim laporan")
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
                StatusRequest.ERROR -> {
                    hideLoading()
                    if (it.failureModel?.msgShow.isLowerContains("JWT expired")) {
                        logoutPSC()
                    } else {
                        DialogMessage(this)
                            .setTitle("Gagal mengirim laporan")
                            .setMessage(it.failureModel?.msgShow)
                            .showDialog()
                    }
                }
            }
        }
    }

    private fun logoutPSC() {
        viewModel?.repository?.logoutPSC()

        finish()
        val intent = Intent(this, PscActivity::class.java)
        startActivity(intent)
    }

    private fun showDialogCategory() {
        val dialog = DialogSelectCategory(this)
        dialog.setListener(object : DialogSelectCategoryListener {
            override fun onClick(dialog: DialogSelectCategory, category: String) {
                dialog.dismiss()
                when (category) {
                    PSC_ACCIDENT -> finalCategory = "Kecelakaan"
                    PSC_DISASTER -> finalCategory = "Bencana"
                    PSC_REFERENCE -> finalCategory = "Referensi"
                    PSC_MATERNITY -> finalCategory = "Persalinan"
                    else -> finish()
                }
                binding?.aprTvCategory?.text = finalCategory
            }
        })
        dialog.setCancelable(false)
        dialog.show()
    }

    //LOCATION

    //Step 1. Mengatur konfigurasi permintaan mendapatkan lokasi
    private fun setupLocation() {
        //Set Location Request
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                setupAddress(p0.lastLocation.latitude, p0.lastLocation.longitude)
            }
        }
    }

    //Step 2. Cek ijin mendapatkan lokasi pada perangkat
    private fun checkAppPermission() {
        helperRequestPermission(object : HelperPermissionListener {
            override fun allPermissionGranted() {
                getMyLocation()
            }

            override fun anyPermissionDenied() {
                checkAppPermission()
            }

            override fun anyPermissionPermantentlyDenied() {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, RC_PERMISSION)
            }
        })
    }

    //Step 3. Berusaha mendapatkan lokasi saat ini
    @SuppressLint("MissingPermission")
    fun getMyLocation() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        //Cek setelan lokasi terpenuhi atau tidak
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        //Jika Task (Setelan terpenuhi) dan berhasil
        task.addOnSuccessListener {
            //Mencoba mendapatkan lokasi terakhir
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                //lakukan try-catch, jaga2 jika hasilnya berupa null
                try {
                    //todo
                    Log.d("GP5", "getMyLocation: ${it.latitude} ${it.longitude}")
                    setupAddress(it.latitude, it.longitude)
                } catch (e: Exception) {
                    //Jika hasilnya null, minta untuk melakukan update location
                    requestUpdateLocation()
                }
            }
        }

        //Jika Task tidak terpenuhi
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                //Mencoba untuk menampilkan dialog menghidupkan GPS
                try {
                    //Tampilkan dialog meminta untuk menghidupkan GPS
                    it.startResolutionForResult(this, RC_GPS)
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }

    //Step 4. Mencatat lat&long dan Konversi lat&long menjadi alamat
    private fun setupAddress(latitude: Double, longitude: Double) {
        finalLatitude = latitude
        finalLongitude = longitude

        val address = latlongToAddress(latitude, longitude)
        binding?.aprTvLocation?.text = address
    }

    //Step 5. Jika lokasi melenceng, gunakan ini untuk refresh mendapatkan lokasi terupdate
    @SuppressLint("MissingPermission")
    private fun requestUpdateLocation() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    //END LOCATION

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.aprTvCategory -> showDialogCategory()
            binding?.aprIvGps -> requestUpdateLocation()
            binding?.aprLlPickPhoto -> {
                tempFile = createImageFile()
                openCamera(tempFile, RC_CAMERA, appId)
            }
            binding?.aprBtnSend -> {
                val body = PostReportBodyModel(
                    additionalInformation = binding?.aprTilAccidentDetail?.editText?.text.toString(),
                    approximateAddress = binding?.aprTvLocation?.text.toString(),
                    location = PscLocationModel(finalLatitude, finalLongitude),
                    incidentName = finalCategory
                )
                viewModel?.postNewReport(finalPhoto, body)
            }
            binding?.aprTvLogout -> {
                logoutPSC()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        lifecycleScope.launch {
                            tempFile?.let {
                                tempFile = Compressor.compress(this@PscReportActivity, it)
                                finalPhoto.add(
                                    PscPhotoModel(
                                        filename = tempFile?.name,
                                        fileImage = tempFile
                                    )
                                )
                                tempFile = null
                                photoAdapter.updatePhoto(finalPhoto)
                            }
                        }
                    } catch (e: Exception) {
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
}