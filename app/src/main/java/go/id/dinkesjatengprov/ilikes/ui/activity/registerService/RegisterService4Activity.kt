package go.id.dinkesjatengprov.ilikes.ui.activity.registerService

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.RegionModel
import go.id.dinkesjatengprov.ilikes.data.model.SelectionModel
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegisterService4Binding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogMessage
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionRegion
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogSelectionRegionListener

class RegisterService4Activity :
    BaseActivity<ActivityRegisterService4Binding, RegisterServiceViewModel>(),
    View.OnClickListener {

    var bookingModel: BookingModel? = null

    //SelectionProvince
    var selectedProvince: SelectionModel? = null
    var listProvince: ArrayList<SelectionModel>? = null

    //Selection City
    var selectedCity: SelectionModel? = null
    var listCity: ArrayList<SelectionModel>? = null

    //Selection District
    var selectedDistrict: SelectionModel? = null
    var listDistrict: ArrayList<SelectionModel>? = null

    //Selection Subdistrict
    var selectedSubdistrict: SelectionModel? = null
    var listSubdistrict: ArrayList<SelectionModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterService4Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterServiceViewModel::class.java]
        setupObserver()

        bookingModel = intent.getParcelableExtra(BOOKING_MODEL)
        initView()

        //Setting Action Bat
        supportActionBar?.title =
            if (bookingModel?.id == null) "Tambah Data Pasien Baru" else "Daftar Pasien"
        enableHomeButton()

        setEndIconClickListener()

        binding?.regBtnNext?.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel?.province?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    listProvince = it.data
                    setSelectionProvince()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Mendapatkan Daftar Provinsi")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }

        viewModel?.city?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    listCity = it.data
                    setSelectionCity()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Mendapatkan Daftar Kabupaten.Kota")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }

        viewModel?.district?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    listDistrict = it.data
                    setSelectionDistrict()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Mendapatkan Daftar Kecamatan")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }

        viewModel?.subdistrict?.observe(this) {
            when (it.statusRequest) {
                StatusRequest.LOADING -> showLoading()
                StatusRequest.SUCCESS -> {
                    hideLoading()
                    listSubdistrict = it.data
                    setSelectionSubdistrict()
                }
                StatusRequest.ERROR -> {
                    hideLoading()
                    DialogMessage(this)
                        .setTitle("Gagal Mendapatkan Daftar Kelurahan")
                        .setMessage(it.failureModel?.msgShow)
                        .showDialog()
                }
            }
        }
    }

    private fun setEndIconClickListener() {
        binding?.regTilProvince?.setEndIconOnClickListener {
            if (viewModel?.province?.value?.data == null) viewModel?.getProvince() else setSelectionProvince()
        }

        binding?.regTilCity?.setEndIconOnClickListener {
            if (selectedProvince == null) {
                binding?.regTilProvince?.error = "Pilih Provinsi dahulu"
                binding?.regTilProvince?.errorIconDrawable = null
                binding?.regTilProvince?.requestFocus()
            } else {
                binding?.regTilProvince?.isErrorEnabled = false
                if (listCity == null) viewModel?.getCity(selectedProvince?.code) else setSelectionCity()
            }
        }

        binding?.regTilDistrict?.setEndIconOnClickListener {
            if (selectedCity == null) {
                binding?.regTilCity?.error = "Pilih Kabupaten/Kota dahulu"
                binding?.regTilCity?.errorIconDrawable = null
                binding?.regTilCity?.requestFocus()
            } else {
                binding?.regTilCity?.isErrorEnabled = false
                if (listCity == null) viewModel?.getDistrict(selectedCity?.code) else setSelectionDistrict()
            }
        }

        binding?.regTilSubdistrict?.setEndIconOnClickListener {
            if (selectedDistrict == null) {
                binding?.regTilDistrict?.error = "Pilih Kecamatab dahulu"
                binding?.regTilDistrict?.errorIconDrawable = null
                binding?.regTilDistrict?.requestFocus()
            } else {
                binding?.regTilDistrict?.isErrorEnabled = false
                if (listCity == null) viewModel?.getSubdistrict(selectedDistrict?.code) else setSelectionSubdistrict()
            }
        }
    }

    private fun setSelectionSubdistrict() {
        DialogSelectionRegion(
            this,
            "Pilih Kelurahan",
            listSubdistrict,
            selectedSubdistrict
        ).setListenerButtonPrimary(object : DialogSelectionRegionListener {
            override fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?) {
                dialog.dismiss()
                selectedSubdistrict = selectedRegion
                binding?.regMactSubdistrict?.setText(selectedSubdistrict?.name)
            }
        }).show()
    }

    private fun setSelectionDistrict() {
        DialogSelectionRegion(
            this,
            "Pilih Kecamatan",
            listDistrict,
            selectedDistrict
        ).setListenerButtonPrimary(object : DialogSelectionRegionListener {
            override fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?) {
                dialog.dismiss()
                if (selectedRegion != selectedDistrict) {
                    resetSelectedAddress(SUBDISTRICT)
                }
                selectedDistrict = selectedRegion
                binding?.regMactDistrict?.setText(selectedDistrict?.name)
            }
        }).show()
    }

    private fun setSelectionCity() {
        DialogSelectionRegion(
            this,
            "Pilih Kabupaten/Kota",
            listCity,
            selectedCity
        ).setListenerButtonPrimary(object : DialogSelectionRegionListener {
            override fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?) {
                dialog.dismiss()
                if (selectedRegion != selectedCity) {
                    resetSelectedAddress(DISTRICT)
                    resetSelectedAddress(SUBDISTRICT)
                }
                selectedCity = selectedRegion
                binding?.regMactCity?.setText(selectedCity?.name)
            }
        }).show()
    }

    private fun setSelectionProvince() {
        DialogSelectionRegion(
            this,
            "Pilih Provinsi",
            listProvince,
            selectedProvince
        ).setListenerButtonPrimary(object : DialogSelectionRegionListener {
            override fun onClick(dialog: DialogSelectionRegion, selectedRegion: SelectionModel?) {
                dialog.dismiss()
                if (selectedRegion != selectedProvince) {
                    resetSelectedAddress(CITY)
                    resetSelectedAddress(DISTRICT)
                    resetSelectedAddress(SUBDISTRICT)
                }
                selectedProvince = selectedRegion
                binding?.regMactProvince?.setText(selectedProvince?.name)
            }
        }).show()
    }

    /**
     * Use to reset selected address like :
     * - list of address
     * - autocomplete adapter of address
     * - selected address variable
     * - view of address
     *
     * @param TAG_ADDRESS is address has been reset
     * */
    private fun resetSelectedAddress(TAG_ADDRESS: String) {
        when (TAG_ADDRESS) {
            PROVINCE -> {
                listProvince = ArrayList()
                selectedProvince = null
                binding?.regMactProvince?.text = null
                binding?.regMactProvince?.setHint("Pilih Provinsi")
            }
            CITY -> {
                listCity = null
                selectedCity = null
                binding?.regMactCity?.text = null
                binding?.regMactCity?.setHint("Pilih Kab/Kota")
            }
            DISTRICT -> {
                listDistrict = null
                selectedDistrict = null
                binding?.regMactDistrict?.text = null
                binding?.regMactDistrict?.setHint("Pilih Kecamatan")
            }
            SUBDISTRICT -> {
                listSubdistrict = null
                selectedSubdistrict = null
                binding?.regMactSubdistrict?.text = null
                binding?.regMactSubdistrict?.setHint("Pilih Kelurahan")
            }
        }
    }

    private fun initView() {
        val isDomicileSameKtp = intent.getBooleanExtra(DOMICILE_AND_KTP, false)
        if (isDomicileSameKtp) {
            val ktpAddress = bookingModel?.patientIdentity?.addressKtp
            bookingModel?.patientIdentity?.addressDomicile = ktpAddress
        }

        binding?.regMactAddress?.setText(bookingModel?.patientIdentity?.addressDomicile?.address)
        binding?.regMactRt?.setText(bookingModel?.patientIdentity?.addressDomicile?.rt)
        binding?.regMactRw?.setText(bookingModel?.patientIdentity?.addressDomicile?.rw)
        binding?.regMactProvince?.setText(bookingModel?.patientIdentity?.addressDomicile?.province?.name)
        selectedProvince = bookingModel?.patientIdentity?.addressDomicile?.province
        binding?.regMactCity?.setText(bookingModel?.patientIdentity?.addressDomicile?.city?.name)
        selectedCity = bookingModel?.patientIdentity?.addressDomicile?.city
        binding?.regMactDistrict?.setText(bookingModel?.patientIdentity?.addressDomicile?.district?.name)
        selectedDistrict = bookingModel?.patientIdentity?.addressDomicile?.district
        binding?.regMactSubdistrict?.setText(bookingModel?.patientIdentity?.addressDomicile?.village?.name)
        selectedSubdistrict = bookingModel?.patientIdentity?.addressDomicile?.village
    }

    override fun onClick(p0: View?) {
        if (!validation()) return
        val domicileAddress = RegionModel(
            address = binding?.regMactAddress?.text?.toString(),
            rt = binding?.regMactRt?.text?.toString(),
            rw = binding?.regMactRw?.text?.toString(),
            province = selectedProvince,
            city = selectedCity,
            district = selectedDistrict,
            village = selectedSubdistrict
        )
        bookingModel = bookingModel?.copy(
            patientIdentity = bookingModel?.patientIdentity?.copy(
                addressDomicile = domicileAddress
            )
        )
        val intent = Intent(this, RegisterService5Activity::class.java)
        intent.putExtra(BOOKING_MODEL, bookingModel)
        startActivity(intent)
    }

    private fun validation(): Boolean {
        binding?.regTilAddress?.isErrorEnabled = false
        binding?.regTilRt?.isErrorEnabled = false
        binding?.regTilRw?.isErrorEnabled = false

        if (binding?.regTilAddress?.isNotEmpty() == false) return false
        if (binding?.regTilRt?.isNotEmpty() == false) return false
        if (binding?.regTilRw?.isNotEmpty() == false) return false
        if (binding?.regTilProvince?.isNotEmpty() == false) return false
        if (binding?.regTilCity?.isNotEmpty() == false) return false
        if (binding?.regTilDistrict?.isNotEmpty() == false) return false
        if (binding?.regTilSubdistrict?.isNotEmpty() == false) return false
        return true
    }

    override fun onBackPressed() {
        if (!helper_confirmation_backpressed) {
            openDialogConfirmationBack()
        } else {
            super.onBackPressed()
        }
    }
}