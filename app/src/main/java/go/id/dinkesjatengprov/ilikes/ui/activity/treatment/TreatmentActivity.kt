package go.id.dinkesjatengprov.ilikes.ui.activity.treatment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.adapter.TreatmentAdapter
import go.id.dinkesjatengprov.ilikes.data.static.treatmentList
import go.id.dinkesjatengprov.ilikes.data.static.treatmentListRsud
import go.id.dinkesjatengprov.ilikes.databinding.ActivityTreatmentBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class TreatmentActivity : BaseActivity<ActivityTreatmentBinding, TreatmentViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreatmentBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[TreatmentViewModel::class.java]
        setupObserver()

        supportActionBar?.title = "Daftar Berobat"
        enableHomeButton()

        binding?.taRv?.layoutManager = LinearLayoutManager(this)
        binding?.taRv?.adapter = TreatmentAdapter(treatmentList)

        binding?.taRvrsud?.layoutManager = LinearLayoutManager(this)
        binding?.taRvrsud?.adapter = TreatmentAdapter(treatmentListRsud)

    }

    private fun setupObserver() {

    }
}