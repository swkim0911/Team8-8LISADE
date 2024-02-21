package com.softeer.togeduck.ui.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.di.DataStoreManager
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

//            val response = loginRepository.login(LoginRequest("user1", "password1"))
//            if (response.isSuccessful) {
//                val sessionId = response.headers()["Set-Cookie"]
//                sessionId?.let {
//                    viewModelScope.launch {
//                        sessionStore(sessionId)
//                        val data = dataStoreManager.userSessionIdFlow.first()
//                        Log.d("TESTLOG", data.toString())
//                    }
//                }
//            } else {
//
//            }

        }
    }

//    private suspend fun sessionStore(sessionId: String) {
//        dataStoreManager.storeUser(sessionId)
//    }
}