package org.wit.livedive.models

interface UserStore {


    fun findAll(): List<UserModel>
    fun createUser(user:UserModel)
    fun findUserByEmail(email: String): UserModel?
    fun updateUser(user: UserModel)
}