package go.id.dinkesjatengprov.ilikes.data.remote.interceptor

import android.app.Activity
import android.content.Intent
import android.util.Log
import go.id.dinkesjatengprov.ilikes.data.local.sharedpreference.SharedPrefManager
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.peli.PeliActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(val token: String?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()

//        newRequest.removeHeader("Content-Type")
//        newRequest.addHeader("Content-Type", "application/json")
        newRequest.removeHeader("Accept")
        newRequest.addHeader("Accept", "application/json")

        if (token != null && token.isNotBlank()) {
            newRequest.addHeader("Authorization", "Bearer $token")
            Log.d("4UTH", "intercept: $token")
        }

        return chain.proceed(newRequest.build())
    }
}

class HeaderInterceptor2() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .removeHeader("Content-Type")
                    .addHeader(
                        "Content-Type",
                        "application/json"
                    )
                    .build()
            )
        }
}

class UnauthorizedInterceptor(val context: Activity) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder().build()

        val response: Response = chain.proceed(newRequest)
        if (response.code == 401) {
            SharedPrefManager(context).loggedOut()

            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            return response
        }
        return response
    }
}