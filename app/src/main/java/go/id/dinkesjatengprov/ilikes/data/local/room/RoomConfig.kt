package go.id.dinkesjatengprov.ilikes.data.local.room

import android.content.Context
import androidx.room.*
import go.id.dinkesjatengprov.ilikes.data.local.converters.RolesConverters
import go.id.dinkesjatengprov.ilikes.data.model.psc.AccountPscModel

@Database(
    entities = [AccountPscModel::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(RolesConverters::class)
abstract class RoomConfig : RoomDatabase() {

    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: RoomConfig? = null

        fun getInstance(context: Context): RoomConfig {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomConfig::class.java,
                        "paijo_dinkesjatengprov.db"
                    ).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}