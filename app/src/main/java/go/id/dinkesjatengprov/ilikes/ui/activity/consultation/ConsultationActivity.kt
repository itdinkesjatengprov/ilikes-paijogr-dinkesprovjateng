package go.id.dinkesjatengprov.ilikes.ui.activity.consultation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.ConsultationAdapter
import go.id.dinkesjatengprov.ilikes.data.static.consultantList
import go.id.dinkesjatengprov.ilikes.databinding.ActivityConsultationBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class ConsultationActivity : BaseActivity<ActivityConsultationBinding, ConsultationViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Daftar Aplikasi Konsultasi Dokter"
        enableHomeButton()

        binding?.rv?.layoutManager = LinearLayoutManager(this)
        binding?.rv?.adapter = ConsultationAdapter(consultantList)
    }
}