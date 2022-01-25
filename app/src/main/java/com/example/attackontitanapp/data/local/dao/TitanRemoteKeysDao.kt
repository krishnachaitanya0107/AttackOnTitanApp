package com.example.attackontitanapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attackontitanapp.domain.model.TitanRemoteKeys

@Dao
interface TitanRemoteKeysDao {

    @Query("SELECT * FROM titan_remote_keys_table WHERE id = :titanId")
    suspend fun getRemoteKeys(titanId: Int): TitanRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(titanRemoteKeys: List<TitanRemoteKeys>)

    @Query("DELETE FROM titan_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}