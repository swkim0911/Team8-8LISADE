package com.softeer.togeduck.utils

import com.softeer.togeduck.data.local.datasource.UserDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CookieInterceptor @Inject constructor(private val userDataStore: UserDataStore) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (isExcludedApi(request.url.toString())) {
            return chain.proceed(request)
        }

        val requestBuilder = request.newBuilder()

        val cookie = runBlocking {
            userDataStore.getUserSessionId.first().toString()
        }
        requestBuilder.addHeader("Cookie", "JSESSIONID=${cookie};")

        return chain.proceed(requestBuilder.build())
    }

    private fun isExcludedApi(url: String): Boolean {
        return url.contains("/users/login")
    }
}