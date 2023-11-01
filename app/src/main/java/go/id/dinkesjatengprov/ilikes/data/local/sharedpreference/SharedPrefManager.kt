package go.id.dinkesjatengprov.ilikes.data.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefManager(context: Context) {

    companion object {
        private const val PREF_FIlE_NAME = "PREF_FILE_NAME"

        //Shared Pref of PAIJO
        private const val PREF_KEY_IS_USER_LOGGED_IN = "PREF_KEY_IS_USER_LOGGED_IN"
        private const val PREF_KEY_TOKEN = "PREF_KEY_TOKEN"

        //Shared Pref of PELI
        private const val PREF_KEY_IS_USER_LOGGED_IN_PELI = "PREF_KEY_IS_USER_LOGGED_IN_PELI"
        private const val PREF_KEY_TOKEN_PELI = "PREF_KEY_TOKEN_PELI"
        private const val PREF_KEY_NAME_PELI = "PREF_KEY_NAME_PELI"
        private const val PREF_KEY_NIK_PELI = "PREF_KEY_NIK_PELI"
        private const val PREF_KEY_TOKEN_CORJAT = "PREF_KEY_TOKEN_CORJAT"

        //Shared Pref of Tanggap
        private const val PREF_KEY_IS_USER_LOGGED_IN_PSC = "PREF_KEY_IS_USER_LOGGED_IN_PSC"
        private const val PREF_KEY_TOKEN_PSC = "PREF_KEY_TOKEN_PSC"
    }

    private val mPreference: SharedPreferences =
        context.getSharedPreferences(PREF_FIlE_NAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = mPreference.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false)
        set(value) = mPreference.edit { putBoolean(PREF_KEY_IS_USER_LOGGED_IN, value) }

    var token: String?
        get() = mPreference.getString(PREF_KEY_TOKEN, "")
        set(value) = mPreference.edit { putString(PREF_KEY_TOKEN, value) }

    var isLoggedInPeli: Boolean
        get() = mPreference.getBoolean(PREF_KEY_IS_USER_LOGGED_IN_PELI, false)
        set(value) = mPreference.edit { putBoolean(PREF_KEY_IS_USER_LOGGED_IN_PELI, value) }

    var tokenPeli: String?
        get() = mPreference.getString(PREF_KEY_TOKEN_PELI, null)
        set(value) = mPreference.edit { putString(PREF_KEY_TOKEN_PELI, value) }

    var namePeli: String?
        get() = mPreference.getString(PREF_KEY_NAME_PELI, null)
        set(value) = mPreference.edit { putString(PREF_KEY_NAME_PELI, value) }

    var nikPeli: String?
        get() = mPreference.getString(PREF_KEY_NIK_PELI, null)
        set(value) = mPreference.edit { putString(PREF_KEY_NIK_PELI, value) }

    var tokenCorjat: String?
        get() = mPreference.getString(PREF_KEY_TOKEN_CORJAT, null)
        set(value) = mPreference.edit { putString(PREF_KEY_TOKEN_CORJAT, value) }

    var isLoggedInPsc: Boolean
        get() = mPreference.getBoolean(PREF_KEY_IS_USER_LOGGED_IN_PSC, false)
        set(value) = mPreference.edit { putBoolean(PREF_KEY_IS_USER_LOGGED_IN_PSC, value) }

    var tokenPSC: String?
        get() = mPreference.getString(PREF_KEY_TOKEN_PSC, null)
        set(value) = mPreference.edit { putString(PREF_KEY_TOKEN_PSC, value) }

    fun loginPSC(token: String?){
        tokenPSC = token
        isLoggedInPsc = true
    }

    fun logoutPSC(){
        tokenPSC = null
        isLoggedInPsc = false
    }

    fun logoutPeli(){
        isLoggedInPeli = false
        tokenPeli = null
        namePeli = null
        nikPeli = null
        tokenCorjat = null
    }

    fun loggedIn(token: String?) {
        isLoggedIn = true
        this.token = token
    }

    fun loggedOut() {
        isLoggedIn = false
        token = null

        logoutPSC()
        logoutPeli()
    }

}