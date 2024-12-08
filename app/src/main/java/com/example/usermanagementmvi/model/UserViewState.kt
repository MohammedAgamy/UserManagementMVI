package com.example.usermanagementmvi.model

data class UserViewState(
    val isLoading: Boolean = false,
    val users: List<UsersModel> = emptyList(),
    val name: String = "",
    val email: String = "",
    val nameError: Boolean = false,
    val emailError: Boolean = false
)

/*It holds all the necessary data required to represent the current state of the UI, including*/