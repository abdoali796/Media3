package com.abdoali.mymidia3.data.database.favorite.item

import android.net.Uri
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun item(uri: ItemID)

    @Query("SELECT * from itemURl")
    fun getALLItem(): Flow<List<ItemID>>

    @Query("DELETE FROM itemURl WHERE uri=:uri")
    suspend fun deleteItem(uri: String)

}