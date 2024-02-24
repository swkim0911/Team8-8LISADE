package com.softeer.togeduck.di

import com.softeer.togeduck.data.remote.datasource.intro.UserRemoteDataSource
import com.softeer.togeduck.data.remote.service.UserService
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import com.softeer.togeduck.data.remote.service.ReserveStatusService
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
    fun provideLoginRemoteDataSource(service: UserService): UserRemoteDataSource {
        return UserRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideReserveStatusRemoteDataSource(service: ReserveStatusService): ReserveStatusRemoteDataSource {
        return ReserveStatusRemoteDataSource(service)
    }
}