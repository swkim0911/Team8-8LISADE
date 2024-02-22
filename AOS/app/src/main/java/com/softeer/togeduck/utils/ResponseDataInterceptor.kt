package com.softeer.togeduck.utils

import android.util.Log
import com.softeer.togeduck.data.dto.BaseResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseDataInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val responseBody = response.body!!
            val contentType = responseBody.contentType()
            val responseData = responseBody.string()

            val type = Types.newParameterizedType(BaseResponse::class.java, Any::class.java)
            val serializationAdapter = moshi.adapter<BaseResponse<Any>>(type)
            val data = serializationAdapter.fromJson(responseData)?.result

            val deserializationAdapter = moshi.adapter(Any::class.java)
            val jsonData = deserializationAdapter.toJson(data)

            val newResponseBody = jsonData.toString().toResponseBody(contentType)

            return response.newBuilder().body(newResponseBody).build()
        }
        return response
    }

}