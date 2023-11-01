package go.id.dinkesjatengprov.ilikes.helper

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.data.model.BookingModel
import go.id.dinkesjatengprov.ilikes.data.model.ClinicModel
import go.id.dinkesjatengprov.ilikes.data.model.LicenseRequirementModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


fun createDummyAddress(TAG_ADDRESS: String): ArrayList<String> {
    val list = ArrayList<String>()
    for (i in 1..15) {
        list.add("$TAG_ADDRESS $i")
    }
    return list
}

fun Activity.createDummyListBooking(filename: String): ArrayList<BookingModel>{
    val json = loadJSONFromAsset(filename)
    Log.d("B00KING", "createDummyListBooking: $json")
    val list = ArrayList<BookingModel>()
    val model : BookingModel = Gson().fromJson(json, BookingModel::class.java)
    for (i in 0..10){
        list.add(model.copy(id = "$i"))
    }
    return list
}

fun Activity.createDummyListClinic(filename: String): ArrayList<ClinicModel>{
    val json = loadJSONFromAsset(filename)
    Log.d("B00KING", "createDummyListClinic: $json")
    val list = ArrayList<ClinicModel>()
    val type = object : TypeToken<ArrayList<ClinicModel?>>() {}.type
    val model : ArrayList<ClinicModel> = Gson().fromJson(json, type)
    return model
}

fun Activity.createDummyList(filename: String): ArrayList<LicenseRequirementModel>{
    val json = loadJSONFromAsset(filename)
    val type = object : TypeToken<ArrayList<LicenseRequirementModel?>>() {}.type
    val model : ArrayList<LicenseRequirementModel> = Gson().fromJson(json, type)
    return model
}

fun listCarouselDummy(): MutableList<CarouselItem>{
    return mutableListOf<CarouselItem>(
        CarouselItem(imageDrawable = R.drawable.banner_dummy1),
        CarouselItem(imageDrawable = R.drawable.banner_dummy5),
        CarouselItem(imageDrawable = R.drawable.banner_dummy2),
        CarouselItem(imageDrawable = R.drawable.banner_dummy3),
        CarouselItem(imageDrawable = R.drawable.banner_dummy4),
    )
}