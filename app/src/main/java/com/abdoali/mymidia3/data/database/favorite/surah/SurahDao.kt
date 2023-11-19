package com.abdoali.mymidia3.data.database.favorite.surah

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SurahDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertID(id: SurahID)

    @Query("SELECT * FROM surahID")
    fun getAllSurah(): Flow<List<Int>>

    @Query("DELETE FROM surahID WHERE id= :id")
    suspend fun deleteArtist(id: Int)

}