package go.id.dinkesjatengprov.ilikes.helper.ui

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

fun Activity.openCamera(file: File?, requestCode: Int, appId: String) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (takePictureIntent.resolveActivity(packageManager) != null) {
        if (file != null) {
            val photoURI = FileProvider.getUriForFile(
                this,
                appId,
                file
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, requestCode)
        }
    }
}

fun Activity.openGallery(requestCode: Int) {
    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivityForResult(pickPhoto, requestCode)
}