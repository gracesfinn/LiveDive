package org.wit.livedive.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.models.DiveModel

class MainApp: Application(), AnkoLogger {

    val dives = ArrayList<DiveModel>()

    override fun onCreate() {
        super.onCreate()
        info("LiveDive started")
        dives.add(DiveModel("One", "About One"))
        dives.add(DiveModel("Two", "About Two"))
    }
}