package com.softeer.togeduck.ui.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.softeer.togeduck.data.dto.request.FcmTokenRequest
import com.softeer.togeduck.data.dto.request.LoginRequest
import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: UserRepository,
) : ViewModel() {
    fun saveSessionId() {
        viewModelScope.launch {
            val response = loginRepository.login(LoginRequest("user1", "password1"))
            if (response.isSuccessful) {
                val sessionId = extractSessionId(response.headers()["Set-Cookie"])
                sessionId?.let {
                    viewModelScope.launch {
                        sessionStore(it)
                    }
                }

                sendFcmToken()
            } else {

            }
        }
    }

    private fun sendFcmToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            val token = it

            viewModelScope.launch {
                val response = loginRepository.sendFcmToken(FcmTokenRequest(token))
                if (response.isSuccessful) {

                } else {

                }
            }
        }
    }

    private suspend fun sessionStore(sessionId: String) {
        loginRepository.storeUser(sessionId)
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