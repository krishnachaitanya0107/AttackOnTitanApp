package com.example.attackontitanapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.attackontitanapp.domain.model.Titan

@Dao
interface TitanDao {

    @Query("SELECT * FROM titans_table ORDER BY id ASC")
    fun getAllTitans(): PagingSource<Int, Titan>

    @Query("SELECT * FROM titans_table WHERE id=:heroId")
    fun getSelectedTitan(heroId: Int): Titan

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTitans(titans: List<Titan>)

    @Query("DELETE FROM titans_table")
    suspend fun deleteAllTitans()

}