package com.example.attackontitanapp.domain.repository

import androidx.paging.PagingData
import com.example.attackontitanapp.domain.model.Titan
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllTitans(): Flow<PagingData<Titan>>
    fun searchTitans(query: String): Flow<PagingData<Titan>>
}