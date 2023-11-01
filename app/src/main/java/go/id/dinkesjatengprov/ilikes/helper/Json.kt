package go.id.dinkesjatengprov.ilikes.helper

import android.app.Activity
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun Activity.loadJSONFromAsset(filename: String): String? {
    var json: String? = null
    json = try {
        val `is`: InputStream = assets.open(filename)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}