package org.wit.livedive.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.livedive.helpers.*
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.DiveStore
import java.util.*

val JSON_FILE = "dives.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<DiveModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class DiveJSONStore : DiveStore, AnkoLogger {

    val context: Context
    var dives = mutableListOf<DiveModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<DiveModel> {
        return dives
    }

    override fun create(dive: DiveModel) {
        dive.id = generateRandomId()
        dives.add(dive)
        serialize()
    }


    override fun update(dive: DiveModel) {
        val divesList = findAll() as ArrayList<DiveModel>
        var foundDive: DiveModel? = divesList.find { p -> p.id == dive.id }
        if (foundDive != null) {
            foundDive.title = dive.title
            foundDive.description = dive.description
            foundDive.image = dive.image
            foundDive.location = dive.location
        }
        serialize()
    }

    override fun delete(dive: DiveModel) {
        val foundDive: DiveModel? = dives.find { it.id == dive.id }
        dives.remove(foundDive)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(dives, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        dives = Gson().fromJson(jsonString, listType)
    }

    override fun findById(id:Long) : DiveModel? {
        val foundDive: DiveModel? = dives.find { it.id == id }
        return foundDive
    }
    override fun clear() {
        dives.clear()
    }
}