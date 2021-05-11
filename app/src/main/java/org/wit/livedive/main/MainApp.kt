package org.wit.livedive.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.models.DiveStore
import org.wit.livedive.models.UserStore
import org.wit.livedive.models.firebase.DiveFireStore
import org.wit.livedive.models.json.DiveJSONStore
import org.wit.livedive.models.json.UserJSONStore
import org.wit.livedive.models.room.DiveStoreRoom

class MainApp: Application(), AnkoLogger {

    //val dives = ArrayList<DiveModel>()

    lateinit var dives : DiveStore
    lateinit var  users : UserStore

    override fun onCreate() {
        super.onCreate()
        //dives = DiveStoreRoom(applicationContext)
        dives = DiveFireStore(applicationContext)
        users = UserJSONStore(applicationContext)
        info("LiveDive started")

    }
}