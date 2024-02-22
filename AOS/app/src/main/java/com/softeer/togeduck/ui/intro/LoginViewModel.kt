package com.softeer.togeduck.ui.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.di.DataStoreManager
import com.softeer.togeduck.data.network.model.LoginRequest
import com.softeer.togeduck.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {
    private val loginRepository = UserRepository()
    fun saveSessionId() {
        viewModelScope.launch {
            dataStoreManager.storeUser("TEST")

            val response = loginRepository.login(LoginRequest("user1", "password1"))
            if (response.isSuccessful) {
                val sessionId = extractSessionId(response.headers()["Set-Cookie"])
                sessionId?.let {
                    viewModelScope.launch {
                        sessionStore(it)
                        val data = dataStoreManager.getUserSessionId.first()
                    }
                }
            } else {

            }

        }
    }

    private suspend fun sessionStore(sessionId: String) {
        dataStoreManager.storeUser(sessionId)
    }


    private fun extractSessionId(sessionId: String?): String? {
        val sessionIdPrefix = "JSESSIONID="
        val parts = sessionId?.split(";")
        parts?.forEach { part ->
            if (part.trim().startsWith(sessionIdPrefix)) {
                return part.substringAfter(sessionIdPrefix)
            }
        }
        return null
    }
}