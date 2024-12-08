package com.example.usermanagementmvi.model

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermanagementmvi.intent.UserIntent
import com.example.usermanagementmvi.repository.UserRepository
import com.example.usermanagementmvi.repository.UserRepositoryImpl
import com.example.usermanagementmvi.ui.SnackbarEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserListViewModel(val repo: UserRepository = UserRepositoryImpl()) : ViewModel() {

    private val _viewState = MutableStateFlow(UserViewState())
    val viewState: StateFlow<UserViewState> = _viewState

    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()


    init {
        handleIntent(UserIntent.LoadUsers)
    }


    fun handleIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadUsers -> loadUsers()
            is UserIntent.AddUser -> addUser(intent.name, intent.email)
            is UserIntent.DeleteUser -> deleteUser(intent.user)
        }
    }


    fun loadUsers() {
        _viewState.value = _viewState.value.copy(isLoading = true)
        viewModelScope.launch {
            val users = repo.getUser()
            _viewState.value = _viewState.value.copy(isLoading = false, users = users)
            _effectChannel.send(SnackbarEffect.ShowSnackbar("Users loaded"))
        }
    }


    fun addUser(name: String, email: String) {
        if (name.isBlank() || email.isBlank()) {
            _viewState.value =
                _viewState.value.copy(nameError = name.isBlank(), emailError = email.isBlank())
            return
        }

        viewModelScope.launch {
            val user = UsersModel(id = (0..1000).random(), name = name, email = email)
            repo.addUser(user)
            _viewState.value = _viewState.value.copy(name = "", email = "")
            loadUsers() // Reload users after adding
            _effectChannel.send(SnackbarEffect.ShowSnackbar("User added successfully"))
        }
    }

    private fun deleteUser(user: UsersModel) {
        viewModelScope.launch {
            repo.deleteUser(user)
            loadUsers() // Reload users after deletion
            _effectChannel.send(SnackbarEffect.ShowSnackbar("User deleted"))
        }
    }

}