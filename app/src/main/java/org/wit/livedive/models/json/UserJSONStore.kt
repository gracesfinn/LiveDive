package org.wit.livedive.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.livedive.helpers.exists
import org.wit.livedive.helpers.read
import org.wit.livedive.helpers.write
import org.wit.livedive.models.UserModel
import org.wit.livedive.models.UserStore
import java.util.ArrayList

val JSON_FILE_USERS = "user.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<ArrayList<UserModel>>() {}.type

class UserJSONStore : UserStore, AnkoLogger {


    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_USERS)) {
            deserializeUser()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {

        if (exists(context, JSON_FILE)) {
            deserializeUser()
        }
        user.userId = generateRandomId()
        users.add(user)
        serializeUser()
    }

    override fun findUserByEmail(email: String): UserModel? {
        for (each in users) {
            if (email == each.email) return each
        }
        return null
    }


    override fun updateUser(user: UserModel) {

        var currentUser: UserModel? = users.find { u -> u.userId == user.userId }

        for (each in users) {
            if (user.userId == each.userId) currentUser = each
        }

        if (currentUser != null) {
            currentUser.email = user.email
            currentUser.password = user.password
        }

        serializeUser()
    }

    private fun serializeUser() {
        val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
        write(context, JSON_FILE_USERS, jsonString)
    }

    private fun deserializeUser() {
        val jsonString = read(context, JSON_FILE_USERS)
        users = Gson().fromJson(jsonString, listTypeUser)
    }


}