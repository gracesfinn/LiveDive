package org.wit.livedive.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.livedive.models.DiveModel


@Database(entities = arrayOf(DiveModel:: class) , version = 1,  exportSchema = false)
internal abstract class Database : RoomDatabase() {

   abstract fun diveDao(): DiveDao
}