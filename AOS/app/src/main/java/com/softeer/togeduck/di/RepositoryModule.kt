package com.softeer.togeduck.di

import android.app.Application
import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.remote.datasource.intro.UserRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import com.softeer.togeduck.data.remote.datasource.chat.ChatDataSource
import com.softeer.togeduck.data.repository.ChatMessageRepository
import com.softeer.togeduck.data.repository.ChatRepository
import com.softeer.togeduck.data.repository.ChatRoomListRepository
import com.softeer.togeduck.data.repository.ReserveStatusRepository
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
        userRemoteDataSource: UserRemoteDataSource, userDataSource: UserDataStore
    ): UserRepository {
        return UserRepository(userRemoteDataSource, userDataSource)
    }

    @Singleton
    @Provides
    fun provideReserveStatusRepository(
        reserveStatusRemoteDataSource: ReserveStatusRemoteDataSource,
    ): ReserveStatusRepository {
        return ReserveStatusRepository(reserveStatusRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideChatRoomListRepository(
        application: Application
    ): ChatRoomListRepository {
        return ChatRoomListRepository(application)
    }

    @Singleton
    @Provides
    fun provideChatMessageRepository(
        application: Application
    ): ChatMessageRepository {
        return ChatMessageRepository(application)
    }

    @Singleton
    @Provides
    fun provideChatRepository(
        chatDataSource: ChatDataSource
    ): ChatRepository {
        return ChatRepository(chatDataSource)
    }
}