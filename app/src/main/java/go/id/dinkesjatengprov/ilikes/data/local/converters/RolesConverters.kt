package go.id.dinkesjatengprov.ilikes.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RolesConverters {
    @TypeConverter
    fun fromDeliveryExchangeList(source: List<String>?): String? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {

        }.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun toDeliveryExchangeList(source: String?): List<String>? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {

        }.type
        return gson.fromJson(source, type)
    }
}