package com.example.usermanagementmvi.repository

import com.example.usermanagementmvi.model.UsersModel


// handel operation do between user and data
class UserRepositoryImpl : UserRepository {

    val users = mutableListOf<UsersModel>()
    override suspend fun getUser(): List<UsersModel> {
        return users
    }

    override suspend fun addUser(usersModel: UsersModel): List<UsersModel> {
        users.add(usersModel)
        return users
    }

    override suspend fun deleteUser(usersModel: UsersModel): List<UsersModel> {
        users.remove(usersModel)
        return users
    }
}