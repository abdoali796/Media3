package com.abdoali.mymidia3.data.database.favorite.item

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemURl")
class ItemID(
    @PrimaryKey(autoGenerate = false)
    val uri: String
)