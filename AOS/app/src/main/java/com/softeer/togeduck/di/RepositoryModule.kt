package com.softeer.togeduck.di

import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.remote.datasource.intro.LoginRemoteDataSource
import com.softeer.togeduck.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        loginRemoteDataSource: LoginRemoteDataSource, userDataSource: UserDataStore
    ): UserRepository {
        return UserRepository(loginRemoteDataSource, userDataSource)
    }
}