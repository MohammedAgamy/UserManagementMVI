package com.example.usermanagementmvi.intent

import com.example.usermanagementmvi.model.UsersModel

/*The UserIntent sealed class encapsulates all the
possible actions a user can take on the user management screen*/

sealed class UserIntent {
    object LoadUsers : UserIntent()
    data class AddUser(val name: String, val email: String) : UserIntent()
    data class DeleteUser(val user: UsersModel) : UserIntent()
}
