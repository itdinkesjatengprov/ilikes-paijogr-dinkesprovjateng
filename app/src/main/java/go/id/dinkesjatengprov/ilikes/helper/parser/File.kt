package go.id.dinkesjatengprov.ilikes.helper.parser

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import go.id.dinkesjatengprov.ilikes.helper.TAG_DOWNLOAD_DOCUMNET
import okhttp3.ResponseBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@Throws(IOException::class)
fun Activity.createImageFile(): File? {
    // Create an image file name
    val timeStamp = SimpleDateFormat(SDF_TYPE_yyyy_MM_dd_HH_mm).format(Date())
    val mFileName = "PNG_" + timeStamp + "_"
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(mFileName, ".png", storageDir)
}

fun Context.getRealPathFromUri(contentUri: Uri?): String? {
    return if (contentUri != null) {
        var cursor: Cursor? = null
        try {
            val img = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, img, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            cursor?.getString(columnIndex!!)
        } finally {
            cursor?.close()
        }
    } else {
        null
    }
}

@Throws(IOException::class)
fun getFile(context: Context, uri: Uri): File? {
    val destinationFilename = File(
        context.filesDir.path + File.separatorChar.toString() + queryName(
            context,
            uri
        )
    )
    try {
        context.contentResolver.openInputStream(uri).use { ins ->
            createFileFromStream(
                ins!!,
                destinationFilename
            )
        }
    } catch (ex: Exception) {
        Log.e("Save File", ex.message.toString())
        ex.printStackTrace()
    }
    return destinationFilename
}

fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: Exception) {
        Log.e("Save File", ex.message.toString())
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

fun Context.writeResponseBodyToDisk(name: String?, body: ResponseBody): Boolean {
    return try {
        val futureStudioIconFile =
            File(getExternalFilesDir(null).toString() + File.separator.toString() + "$name")
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val fileSize = body.contentLength()
            var fileSizeDownloaded: Long = 0
            inputStream = body.byteStream()
            outputStream = FileOutputStream(futureStudioIconFile)
            while (true) {
                val read: Int = inputStream.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
                fileSizeDownloaded += read.toLong()

                val percentage = (fileSizeDownloaded / fileSize) * 100
                Log.d(TAG_DOWNLOAD_DOCUMNET, "file download: $fileSizeDownloaded of $fileSize ($percentage)")
            }
            outputStream.flush()
            Log.d(TAG_DOWNLOAD_DOCUMNET, "file download: ${futureStudioIconFile.absolutePath}")
            true
        } catch (e: IOException) {
            Log.e(TAG_DOWNLOAD_DOCUMNET, "file download failed: $${e.message}")
            false
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        Log.e(TAG_DOWNLOAD_DOCUMNET, "file download failed: $${e.message}")
        false
    }
}