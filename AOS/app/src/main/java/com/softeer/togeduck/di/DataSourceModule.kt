package com.softeer.togeduck.di

import com.softeer.togeduck.data.remote.datasource.LoginRemoteDataSource
import com.softeer.togeduck.data.remote.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideLoginRemoteDataSource(service: LoginService): LoginRemoteDataSource {
        return LoginRemoteDataSource(service)
    }
}