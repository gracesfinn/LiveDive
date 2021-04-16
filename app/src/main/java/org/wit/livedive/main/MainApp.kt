package org.wit.livedive.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.models.DiveStore
import org.wit.livedive.models.json.DiveJSONStore
import org.wit.livedive.models.room.DiveStoreRoom

class MainApp: Application(), AnkoLogger {

    //val dives = ArrayList<DiveModel>()

    lateinit var dives : DiveStore

    override fun onCreate() {
        super.onCreate()
        dives = DiveStoreRoom(applicationContext)
        info("LiveDive started")

    }
}