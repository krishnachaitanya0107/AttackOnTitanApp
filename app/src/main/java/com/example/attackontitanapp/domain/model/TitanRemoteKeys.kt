package com.example.attackontitanapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.attackontitanapp.util.Constants.TITAN_REMOTE_KEYS_DATABASE_TABLE

@Entity(tableName = TITAN_REMOTE_KEYS_DATABASE_TABLE)
data class TitanRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)
