package go.id.dinkesjatengprov.ilikes.data.remote.interceptor

import android.app.Activity
import android.content.Intent
import go.id.dinkesjatengprov.ilikes.data.local.room.RoomConfig
import go.id.dinkesjatengprov.ilikes.data.local.sharedpreference.SharedPrefManager
import go.id.dinkesjatengprov.ilikes.ui.activity.psc.PscActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

class PscHeaderInterceptor() : Interceptor {
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

class PscHeaderInterceptor2(val token: String?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()

        if (token != null && token.isNotBlank()) {
            newRequest.addHeader("Authorization", "$token")
        }

        return chain.proceed(newRequest.build())
    }
}

class PscUnauthorizedInterceptor(val context: Activity) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder().build()

        val response: Response = chain.proceed(newRequest)
        if (response.code == 401) {

            val dao = RoomConfig.getInstance(context).dao
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    dao.clearAccountTable()
                    SharedPrefManager(context).logoutPSC()

                    context.finish()
                    val intent = Intent(context, PscActivity::class.java)
                    context.startActivity(intent)
                }catch (e: Exception){

                }
            }
            return response
        }
        return response
    }
}