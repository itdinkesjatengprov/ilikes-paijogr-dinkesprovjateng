package go.id.dinkesjatengprov.ilikes.data.remote.interceptor

import android.app.Activity
import android.content.Intent
import android.util.Log
import go.id.dinkesjatengprov.ilikes.data.local.sharedpreference.SharedPrefManager
import go.id.dinkesjatengprov.ilikes.ui.activity.peli.PeliActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PeliHeaderInterceptor(val token: String?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()

        newRequest.removeHeader("Accept")
        newRequest.addHeader("Accept", "application/json")

        Log.d("T0K3N-C0RJAT", "intercept: $token")
        if (token != null && token.isNotBlank()) {
            newRequest.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(newRequest.build())
    }
}

class PeliUnauthorizedInterceptor(val context: Activity) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder().build()

        val response: Response = chain.proceed(newRequest)
        if (response.code == 401) {
            SharedPrefManager(context).isLoggedInPeli = false
            SharedPrefManager(context).tokenPeli = null

            val intent = Intent(context, PeliActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            return response
        }
        return response
    }
}