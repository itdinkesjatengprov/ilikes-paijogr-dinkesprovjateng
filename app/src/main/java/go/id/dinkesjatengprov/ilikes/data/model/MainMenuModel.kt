package go.id.dinkesjatengprov.ilikes.data.model

import android.app.Activity
import go.id.dinkesjatengprov.ilikes.ui.activity.treatment.TreatmentActivity

data class MainMenuModel(

    val tag: Int,

    val title: String? = null,

    val icon: Int,

    val tint: Int,

    /**
     * 1 Menggunakan [page]
     * 2 Menggunakan [url]
     * 3 Menggunakan []
     * */
    val type: Int = 0,

    /**
     * Digunakan untuk menu yang jika dipilih akan berpindah ke halaman fitur yang dibuat di aplikasi
     * */
    val page: Any? = null,

    /**
     * Digunakan untuk menu yang jika dipilih akan berpindah ke halaman webview dan menampilkan berdasarkan url
     * */
    val url: String? = null,

    val isLock: Boolean? = false

)