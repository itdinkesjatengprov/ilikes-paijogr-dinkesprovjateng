package go.id.dinkesjatengprov.ilikes.ui.activity.telemedicine

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.ConsultationAdapter
import go.id.dinkesjatengprov.ilikes.adapter.TelemedicineAdapter
import go.id.dinkesjatengprov.ilikes.data.static.consultantList
import go.id.dinkesjatengprov.ilikes.data.static.telemedicineList
import go.id.dinkesjatengprov.ilikes.databinding.ActivityTelemedicineBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class TelemedicineActivity : BaseActivity<ActivityTelemedicineBinding, TelemedicineViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelemedicineBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Telemedisin"
        enableHomeButton()

        binding?.rv?.layoutManager = LinearLayoutManager(this)
        binding?.rv?.adapter = TelemedicineAdapter(telemedicineList)
    }
}