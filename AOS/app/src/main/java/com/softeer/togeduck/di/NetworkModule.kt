package com.softeer.togeduck.di

import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.utils.CookieInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideCookieInterceptor(userDataStore: UserDataStore): CookieInterceptor {
        return CookieInterceptor(userDataStore)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieInterceptor: CookieInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(cookieInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl("http://52.78.152.170:8080/").addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        ).client(okHttpClient).build()
    }

}

