package com.example.randomusers.fragments.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomusers.data.UsersListUiState
import com.example.randomusers.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val usersListUiState = MutableStateFlow<UsersListUiState>(UsersListUiState.Initial)
    val usersUiState: StateFlow<UsersListUiState> = usersListUiState

    fun fetchUsers(isNewUsers: Boolean) {
        viewModelScope.launch {
            usersListUiState.value = UsersListUiState.Loading(true)
            try {
                if (isNewUsers) {
                    getUsersFromApi()
                } else {
                    val users = repository.fetchCachedUsers()
                    if (users.isNotEmpty()) {
                        usersListUiState.value = UsersListUiState.Success(users)
                    } else {
                        getUsersFromApi()
                    }
                }
            } catch (e: Exception) {
                usersListUiState.value = UsersListUiState.Error(e.message ?: "Unknown error")
            } finally {
                usersListUiState.value = UsersListUiState.Loading(false)
            }
        }
    }

    private suspend fun getUsersFromApi() {
        val response = repository.getUsersData()
        if (response.code() == 200) {
            val users = response.body()?.usersList!!
            repository.replaceData(users)
            usersListUiState.value = UsersListUiState.Success(users)
        } else {
            val message = response.message()
            usersListUiState.value = UsersListUiState.Error(message)
        }
    }
}