package com.abdoali.mymidia3.data.database.favorite.artist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertID(id: ArtistID)

    @Query("SELECT * FROM artistID")
    fun getAllArtist(): Flow<List<Int>>

//    @Query("SELECT * FROM artistID WHERE id= :id")
//    fun itFavor(id: Int): Flow<Int>

    @Query("DELETE FROM artistID WHERE id= :id")
    suspend fun deleteArtist(id: Int)

}