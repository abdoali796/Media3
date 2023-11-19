package com.abdoali.mymidia3.data.database.favorite.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artistID")
data class ArtistID(
    @PrimaryKey
    val id: Int
)


