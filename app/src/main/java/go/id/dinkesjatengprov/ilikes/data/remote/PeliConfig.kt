package go.id.dinkesjatengprov.ilikes.data.remote

import android.app.Activity
import android.content.Context
import com.google.gson.GsonBuilder
import go.id.dinkesjatengprov.ilikes.data.local.sharedpreference.SharedPrefManager
import go.id.dinkesjatengprov.ilikes.data.remote.interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PeliConfig {

    var BASE_URL = "https://admin.corona.jatengprov.go.id/"

    val httpInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun setConnection(): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    private fun setConnection(context: Context): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        val unauthorizedInterceptor = PeliUnauthorizedInterceptor(context as Activity)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .addNetworkInterceptor(PeliHeaderInterceptor(SharedPrefManager(context).tokenCorjat))
            .addNetworkInterceptor(unauthorizedInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun setApiService(): ApiInterface = setConnection().create(ApiInterface::class.java)
    fun setApiService(context: Context): ApiInterface =
        setConnection(context).create(ApiInterface::class.java)
}