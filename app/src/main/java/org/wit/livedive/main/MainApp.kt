package org.wit.livedive.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.api.DiveService
import org.wit.livedive.models.DiveStore
import org.wit.livedive.models.firebase.DiveFireStore

class MainApp: Application(), AnkoLogger {

    //val dives = ArrayList<DiveModel>()

    lateinit var dives : DiveStore

    lateinit var  diveService: DiveService

    override fun onCreate() {
        super.onCreate()
        //dives = DiveStoreRoom(applicationContext)
        dives = DiveFireStore(applicationContext)
        info("LiveDive started")
        diveService = DiveService.create()
        info("Dive Service Created")

    }
}