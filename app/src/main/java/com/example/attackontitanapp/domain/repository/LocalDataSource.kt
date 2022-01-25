package com.example.attackontitanapp.domain.repository

import com.example.attackontitanapp.domain.model.Titan

interface LocalDataSource {
    suspend fun getSelectedTitan(heroId: Int): Titan
}