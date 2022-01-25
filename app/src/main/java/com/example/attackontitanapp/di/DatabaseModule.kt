package com.example.attackontitanapp.di

import android.content.Context
import androidx.room.Room
import com.example.attackontitanapp.data.local.AotDatabase
import com.example.attackontitanapp.data.repository.LocalDataSourceImpl
import com.example.attackontitanapp.domain.repository.LocalDataSource
import com.example.attackontitanapp.util.Constants.AOT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AotDatabase {
        return Room.databaseBuilder(
            context,
            AotDatabase::class.java,
            AOT_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: AotDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(
            aotDatabase = database
        )
    }

}