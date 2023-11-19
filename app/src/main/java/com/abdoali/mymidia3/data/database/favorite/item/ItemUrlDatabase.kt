package com.abdoali.mymidia3.data.database.favorite.item

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemID::class] , version = 1 , exportSchema = false)
abstract class ItemUrlDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}