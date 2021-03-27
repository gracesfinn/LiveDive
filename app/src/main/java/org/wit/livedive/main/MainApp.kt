package org.wit.livedive.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.models.DiveJSONStore
import org.wit.livedive.models.DiveMemStore
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.DiveStore

class MainApp: Application(), AnkoLogger {

    //val dives = ArrayList<DiveModel>()

    lateinit var dives : DiveStore

    override fun onCreate() {
        super.onCreate()
        dives = DiveJSONStore(applicationContext)
        info("LiveDive started")

    }
}