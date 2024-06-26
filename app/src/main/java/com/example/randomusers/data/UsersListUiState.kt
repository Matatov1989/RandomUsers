package com.example.randomusers.data

import com.example.randomusers.model.UserModel

sealed class UsersListUiState {
    data object Initial : UsersListUiState()
    data class Success(val users: MutableList<UserModel>) : UsersListUiState()
    data class Error(val message: String) : UsersListUiState()
    data class Loading(val isLoad: Boolean) : UsersListUiState()
}