package org.wit.livedive.models.room

import android.content.Context
import androidx.room.Room
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.DiveStore

class DiveStoreRoom(val context: Context) : DiveStore {

    var dao: DiveDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.diveDao()
    }

    override fun findAll(): List<DiveModel> {
        return dao.findAll()
    }

    override fun findById(id: Long):DiveModel? {
        return dao.findById(id)
    }

    override fun create(dive: DiveModel) {
        dao.create(dive)
    }

    override fun update(dive: DiveModel) {
        dao.update(dive)
    }

    override fun delete(dive: DiveModel) {
        dao.deleteDive(dive)
    }

    override fun clear() {

    }
}

