package com.abdoali.mymidia3.data.database.favorite.surah

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surahID")
data class SurahID(
    @PrimaryKey()
    val id: Int
)