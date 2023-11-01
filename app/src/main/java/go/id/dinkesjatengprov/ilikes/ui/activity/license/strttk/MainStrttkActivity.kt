package go.id.dinkesjatengprov.ilikes.ui.activity.license.strttk

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.RVRequirementAdapter
import go.id.dinkesjatengprov.ilikes.data.model.LicenseModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseBackground
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseColor
import go.id.dinkesjatengprov.ilikes.data.static.setStatusLicenseText
import go.id.dinkesjatengprov.ilikes.databinding.ActivityMainStrttkBinding
import go.id.dinkesjatengprov.ilikes.helper.LICENSE
import go.id.dinkesjatengprov.ilikes.helper.STRTTK
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class MainStrttkActivity : BaseActivity<ActivityMainStrttkBinding, StrttkViewModel>(),
    View.OnClickListener {

    var strttk: LicenseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainStrttkBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableHomeButton()

        viewModel = ViewModelProvider(this, viewModelFactory).get(StrttkViewModel::class.java)
        setupObserver()

        binding?.amsRvRequirement?.layoutManager = LinearLayoutManager(this)
        binding?.amsRvRequirement?.adapter = RVRequirementAdapter()

        initView()

        binding?.amsBtnCreate?.setOnClickListener(this)
        binding?.amsBtnDocument?.setOnClickListener(this)
        binding?.amsBtnResult?.setOnClickListener(this)

//        binding?.amsSrl?.setOnRefreshListener {
//            binding?.amsSrl?.isRefreshing = false
//            viewModel?.getActiveSTRTTK()
//        }
    }

    private fun initView() {
        strttk = intent.getParcelableExtra(STRTTK)
        if (strttk == null) {
            binding?.amsGroupDocument?.visibility = View.GONE
            binding?.amsTvMessage?.visibility = View.VISIBLE
            binding?.amsBtnCreate?.visibility = View.VISIBLE
        } else {
            binding?.amsGroupDocument?.visibility = View.VISIBLE
            binding?.amsTvMessage?.visibility = View.GONE
            binding?.amsBtnCreate?.visibility = View.GONE
            binding?.amsTvStatus?.text = setStatusLicenseText(strttk?.status)
            binding?.amsTvStatus?.background = setStatusLicenseBackground(strttk?.status)
            binding?.amsTvStatus?.setTextColor(setStatusLicenseColor(strttk?.status))
            binding?.amsTvId?.text = strttk?.id
        }

        if (strttk?.status == "Done") {
            binding?.amsBtnResult?.visibility = View.VISIBLE
        } else {
            binding?.amsBtnResult?.visibility = View.GONE
        }
    }

    private fun setupObserver() {
        viewModel?.strttkActive?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    strttk = it.data
                    initView()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    showToast("Gagal mendapatkan status terbaru dokumen")
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding?.amsBtnCreate -> {
                startActivity(Intent(this, FormStrttkActivity::class.java))
            }
            binding?.amsBtnDocument -> {
                val intent = Intent(this, DocumentNewStrttkActivity::class.java)
                intent.putExtra(LICENSE, strttk)
                startActivity(intent)
            }
            binding?.amsBtnResult -> {
                val intent = Intent(this, ResultStrttkActivity::class.java)
                intent.putExtra(LICENSE, strttk)
                startActivity(intent)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel?.getActiveSTRTTK()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history -> startActivity(Intent(this, HistoryStrttkActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}