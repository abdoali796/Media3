package com.abdoali.mymidia3.data.database.favorite.artist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtistID::class] , version = 1 , exportSchema = false)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}