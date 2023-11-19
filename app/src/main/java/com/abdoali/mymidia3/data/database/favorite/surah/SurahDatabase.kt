package com.abdoali.mymidia3.data.database.favorite.surah

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SurahID::class] , version = 1 , exportSchema = false)
abstract class SurahDatabase : RoomDatabase() {
    abstract fun surahDao(): SurahDao
}