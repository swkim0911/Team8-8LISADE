package com.softeer.togeduck.di

import com.softeer.togeduck.data.remote.service.HomeService
import com.softeer.togeduck.data.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideLoginService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeService(
        retrofit: Retrofit
    ):HomeService{
        return retrofit.create(HomeService::class.java)
    }

}