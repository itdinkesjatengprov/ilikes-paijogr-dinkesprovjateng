package go.id.dinkesjatengprov.ilikes.data.remote.model

import com.google.gson.annotations.SerializedName

/** Kelas model ini digunakan untuk menampung error saat mencoba menghubungi server
 *
 * @param code kode error
 * @param msgShow pesan error yang akan ditampilkan pada tampilan
 * @param msgSystem pesan error yang sesuai dengan kesalahan atau dari koneksi API
 * */
data class FailureModel(

    val code: String? = null,

    var msgShow: String? = null,

    var msgSystem: String? = null,

    var throwable: Throwable? = null
)