package com.softeer.togeduck.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.network.model.LoginRequest
import com.softeer.togeduck.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginRepository = UserRepository()

    fun saveSessionId() {
        viewModelScope.launch {
            val loginInfo = loginRepository.login(LoginRequest("user1", "password1"))
        }
    }
}