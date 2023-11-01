package go.id.dinkesjatengprov.ilikes.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import go.id.dinkesjatengprov.ilikes.adapter.AddressAutoCompleteAdapter
import go.id.dinkesjatengprov.ilikes.databinding.ActivityRegister4Binding
import go.id.dinkesjatengprov.ilikes.helper.*
import go.id.dinkesjatengprov.ilikes.helper.ui.isNotEmpty
import go.id.dinkesjatengprov.ilikes.helper.ui.showToast
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.login.LoginActivity

class Register4Activity : BaseActivity<ActivityRegister4Binding, RegisterViewModel>(), View.OnClickListener {

    //Autocomplete Province
    //TODO Change String with address model
    var selectedProvince: String? = null
    var listProvince: ArrayList<String> = ArrayList()
    lateinit var provinceAdapter: AddressAutoCompleteAdapter

    //Autocomplete City
    //TODO Change String with address model
    var selectedCity: String? = null
    var listCity: ArrayList<String> = ArrayList()
    lateinit var cityAdapter: AddressAutoCompleteAdapter

    //Autocomplete District
    //TODO Change String with address model
    var selectedDistrict: String? = null
    var listDistrict: ArrayList<String> = ArrayList()
    lateinit var districtAdapter: AddressAutoCompleteAdapter

    //Autocomplete Subdistrict
    //TODO Change String with address model
    var selectedSubdistrict: String? = null
    var listSubdistrict: ArrayList<String> = ArrayList()
    lateinit var subdistrictAdapter: AddressAutoCompleteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister4Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Setting Action Bat
        supportActionBar?.title = "Kelengkapan Identitas"
        enableHomeButton()

        //Cek jika data domisili sama dengan data ktp
        //TODO Atur listProvince-listSubdistrict sesuai data KTP

        //Setting AutoComplete
        settingAutoCompleteProvince()

        binding?.regBtnNext?.setOnClickListener(this)
    }

    /**
     * Setting Autocomplete of Province and Setting action when province selected
     * */
    private fun settingAutoCompleteProvince() {
        //TODO listProvince change to response of getListProvince from server
        listProvince = createDummyAddress(PROVINCE)

        provinceAdapter = AddressAutoCompleteAdapter(this, listProvince)
        binding?.regMactProvince?.setAdapter(provinceAdapter)
        binding?.regMactProvince?.setOnItemClickListener { parent, view, position, id ->
            //Check jika yang dipilih sekarang sama dengan sebelumnya, maka tidak perlu diganti
            if (selectedProvince != provinceAdapter.getItem(position)) {
                selectedProvince = provinceAdapter.getItem(position)
                binding?.regMactProvince?.setText(selectedProvince)

                //Reset selected city, district and subdistrict
                resetSelectedAddress(CITY)
                resetSelectedAddress(DISTRICT)
                resetSelectedAddress(SUBDISTRICT)

                //TODO Get LIST CITY by selected ID Province
                listCity = createDummyAddress(CITY)
                settingAutoCompleteCity()
            }
        }
    }

    /**
     * Setting Autocomplete of City and Setting action when City selected
     * */
    private fun settingAutoCompleteCity() {
        cityAdapter = AddressAutoCompleteAdapter(this, listCity)
        binding?.regMactCity?.setAdapter(cityAdapter)
        binding?.regMactCity?.setOnItemClickListener { parent, view, position, id ->
            //Check jika yang dipilih sekarang sama dengan sebelumnya, maka tidak perlu diganti
            if (selectedCity != cityAdapter.getItem(position)) {
                selectedCity = cityAdapter.getItem(position)
                binding?.regMactCity?.setText(selectedCity)

                //Reset selected district and subdistrict
                resetSelectedAddress(DISTRICT)
                resetSelectedAddress(SUBDISTRICT)

                //TODO Get LIST DISTRICT by selected ID CITY
                listDistrict = createDummyAddress(DISTRICT)
                settingAutoCompleteDistrict()
            }
        }
    }

    /**
     * Setting Autocomplete of District and Setting action when District selected
     * */
    private fun settingAutoCompleteDistrict() {
        districtAdapter = AddressAutoCompleteAdapter(this, listDistrict)
        binding?.regMactDistrict?.setAdapter(districtAdapter)
        binding?.regMactDistrict?.setOnItemClickListener { parent, view, position, id ->
            //Check jika yang dipilih sekarang sama dengan sebelumnya, maka tidak perlu diganti
            if (selectedDistrict != districtAdapter.getItem(position)) {
                selectedDistrict = districtAdapter.getItem(position)
                binding?.regMactDistrict?.setText(selectedDistrict)

                //Reset selected subdistrict
                resetSelectedAddress(SUBDISTRICT)

                //TODO Get LIST Subdistrict by selected ID District
                listSubdistrict = createDummyAddress(SUBDISTRICT)
                settingAutoCompleteSubdistrict()

            }
        }
    }

    /**
     * Setting Autocomplete of Subdistrict and Setting action when Subdistrict selected
     * */
    private fun settingAutoCompleteSubdistrict() {
        subdistrictAdapter = AddressAutoCompleteAdapter(this, listSubdistrict)
        binding?.regMactSubdistrict?.setAdapter(subdistrictAdapter)
        binding?.regMactSubdistrict?.setOnItemClickListener { parent, view, position, id ->
            //Check jika yang dipilih sekarang sama dengan sebelumnya, maka tidak perlu diganti
            if (selectedSubdistrict != subdistrictAdapter.getItem(position)) {
                selectedSubdistrict = subdistrictAdapter.getItem(position)
                binding?.regMactSubdistrict?.setText(selectedSubdistrict)
            }
        }
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
                provinceAdapter = AddressAutoCompleteAdapter(this, listProvince)
                binding?.regMactProvince?.setAdapter(provinceAdapter)
                selectedProvince = null
                binding?.regMactProvince?.text = null
                binding?.regMactProvince?.setHint("Pilih Provinsi")
            }
            CITY -> {
                listCity = ArrayList()
                cityAdapter = AddressAutoCompleteAdapter(this, listCity)
                binding?.regMactCity?.setAdapter(cityAdapter)
                selectedCity = null
                binding?.regMactCity?.text = null
                binding?.regMactCity?.setHint("Pilih Kab/Kota")
            }
            DISTRICT -> {
                listDistrict = ArrayList()
                districtAdapter = AddressAutoCompleteAdapter(this, listDistrict)
                binding?.regMactDistrict?.setAdapter(districtAdapter)
                selectedDistrict = null
                binding?.regMactDistrict?.text = null
                binding?.regMactDistrict?.setHint("Pilih Kecamatan")
            }
            SUBDISTRICT -> {
                listSubdistrict = ArrayList()
                subdistrictAdapter = AddressAutoCompleteAdapter(this, listSubdistrict)
                binding?.regMactSubdistrict?.setAdapter(subdistrictAdapter)
                selectedSubdistrict = null
                binding?.regMactSubdistrict?.text = null
                binding?.regMactSubdistrict?.setHint("Pilih Kelurahan")
            }
        }
    }

    override fun onBackPressed() {
        if (!helper_confirmation_backpressed) {
            openDialogConfirmationBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding?.regBtnNext -> {
//                if (!validation()){
//                    return
//                }
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        }
    }

    private fun validation(): Boolean {
        binding?.regTilAddress?.isErrorEnabled = false
        binding?.regTilRt?.isErrorEnabled = false
        binding?.regTilRw?.isErrorEnabled = false

        if (binding?.regTilAddress?.isNotEmpty()==false) return false
        if (binding?.regTilRt?.isNotEmpty()==false) return false
        if (binding?.regTilRw?.isNotEmpty()==false) return false
        if (selectedProvince==null){
            showToast("Pilih Provinsi terlebih dahulu")
            return false
        }
        if (selectedCity==null){
            showToast("Pilih Kab/Kota terlebih dahulu")
            return false
        }
        if (selectedDistrict==null){
            showToast("Pilih Kecamatan terlebih dahulu")
            return false
        }
        if (selectedSubdistrict==null){
            showToast("Pilih Kelurahan terlebih dahulu")
            return false
        }
        return false
    }
}