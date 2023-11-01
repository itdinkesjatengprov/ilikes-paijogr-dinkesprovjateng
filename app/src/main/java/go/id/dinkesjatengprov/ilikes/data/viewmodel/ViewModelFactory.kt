package go.id.dinkesjatengprov.ilikes.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.MainRepository
import go.id.dinkesjatengprov.ilikes.ui.activity.consultation.ConsultationViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.forgotPassword.ForgotPasswordViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.register.RegisterViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.registerService.RegisterServiceViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.splash.SplashViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk.StrttkViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.license.tubel.TubelViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.license.uploadDocument.DocumentUploadViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.peli.PeliActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.peli.PeliViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.pmi.PmiViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.psc.PscViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.telemedicine.TelemedicineViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.treatment.TreatmentViewModel
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.WebviewViewModel
import go.id.dinkesjatengprov.ilikes.ui.fragment.license.LicenseViewModel
import go.id.dinkesjatengprov.ilikes.ui.fragment.profil.ProfilViewModel
import go.id.dinkesjatengprov.ilikes.ui.fragment.service.ServiceViewModel

class ViewModelFactory(
    private val application: Application,
    private val repository: MainRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /**
         * AUTH SCREEN
         * ex: Splash, Login, Register
         * */
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(application, repository) as T
        }

        /**
         * MENU SCREEN
         * ex: Menu Utama, Daftar LaKes, Daftar Aplikasi Konsultasi dan Webview, PELI
         * */
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(TreatmentViewModel::class.java)) {
            return TreatmentViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(ConsultationViewModel::class.java)) {
            return ConsultationViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(WebviewViewModel::class.java)) {
            return WebviewViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(PeliViewModel::class.java)) {
            return PeliViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(PscViewModel::class.java)) {
            return PscViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(PmiViewModel::class.java)) {
            return PmiViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(TelemedicineViewModel::class.java)) {
            return TelemedicineViewModel(application, repository) as T
        }

        //Fragment
        if (modelClass.isAssignableFrom(go.id.dinkesjatengprov.ilikes.ui.fragment.home.HomeViewModel::class.java)) {
            return go.id.dinkesjatengprov.ilikes.ui.fragment.home.HomeViewModel(
                application,
                repository
            ) as T
        }
        if (modelClass.isAssignableFrom(ServiceViewModel::class.java)) {
            return ServiceViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(LicenseViewModel::class.java)) {
            return LicenseViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            return ProfilViewModel(application, repository) as T
        }

        //SERVICE
        if (modelClass.isAssignableFrom(RegisterServiceViewModel::class.java)) {
            return RegisterServiceViewModel(application, repository) as T
        }

        /**
        * Daftar VIEWMODEL LICENSE
        * */
        if (modelClass.isAssignableFrom(StrttkViewModel::class.java)) {
            return StrttkViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(TubelViewModel::class.java)) {
            return TubelViewModel(application, repository) as T
        }
        if (modelClass.isAssignableFrom(DocumentUploadViewModel::class.java)) {
            return DocumentUploadViewModel(application, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}