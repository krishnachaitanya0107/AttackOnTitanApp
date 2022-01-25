package com.example.attackontitanapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.attackontitanapp.data.local.dao.TitanDao
import com.example.attackontitanapp.data.local.dao.TitanRemoteKeysDao
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.model.TitanRemoteKeys

@Database(entities = [Titan::class, TitanRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class AotDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): AotDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AotDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AotDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun titanDao(): TitanDao
    abstract fun titanRemoteKeysDao(): TitanRemoteKeysDao

}