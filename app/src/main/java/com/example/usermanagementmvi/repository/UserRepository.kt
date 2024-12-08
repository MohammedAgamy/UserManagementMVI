package com.example.usermanagementmvi.repository

import com.example.usermanagementmvi.model.UsersModel

//The UserRepository interface defines how user data
// is managed within the application. It includes methods
interface UserRepository {
    suspend fun getUser(): List<UsersModel>
    suspend fun addUser(usersModel: UsersModel): List<UsersModel>
    suspend fun deleteUser(usersModel: UsersModel): List<UsersModel>
}