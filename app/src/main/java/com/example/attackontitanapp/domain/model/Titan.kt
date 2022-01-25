package com.example.attackontitanapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.attackontitanapp.util.Constants.TITAN_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TITAN_DATABASE_TABLE)
data class Titan(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val height: Int,
    val type: String,
    val inheritors: List<String>,
    val abilities: List<String>,
    val otherNames: List<String>
)
