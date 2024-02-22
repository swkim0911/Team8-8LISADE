package com.softeer.togeduck.utils

import com.softeer.togeduck.data.local.datasource.UserDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CookieInterceptor @Inject constructor(private val userDataStore: UserDataStore) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val cookie = userDataStore.getUserSessionId.toString()

        requestBuilder.addHeader("Cookie", cookie)

        return chain.proceed(requestBuilder.build())
    }
}