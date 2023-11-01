package go.id.dinkesjatengprov.ilikes.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import go.id.dinkesjatengprov.ilikes.data.model.psc.AccountPscModel

@Dao
interface Dao {

    /******************************* TABLE OF USER PROFILE *****************************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountPscModel?)

    @Query("SELECT * FROM tb_account_psc LIMIT 1")
    fun getOneUser(): AccountPscModel

    @Query("DELETE FROM tb_account_psc")
    fun clearAccountTable()

}