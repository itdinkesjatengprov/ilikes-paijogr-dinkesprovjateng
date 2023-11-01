package go.id.dinkesjatengprov.ilikes.helper

import android.Manifest
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Context.helperRequestPermission(listener: HelperPermissionListener) {
    Dexter.withContext(this)
        .withPermissions(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                when {
                    p0?.areAllPermissionsGranted() == true -> {
                        listener.allPermissionGranted()
                    }
                    p0?.isAnyPermissionPermanentlyDenied == true -> {
                        listener.anyPermissionPermantentlyDenied()
                    }
                    else -> {
                        listener.anyPermissionDenied()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        }).check()
}

interface HelperPermissionListener {
    fun allPermissionGranted()
    fun anyPermissionDenied()
    fun anyPermissionPermantentlyDenied()
}