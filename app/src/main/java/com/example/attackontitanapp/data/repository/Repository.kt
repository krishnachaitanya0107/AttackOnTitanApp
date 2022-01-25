package com.example.attackontitanapp.data.repository

import androidx.paging.PagingData
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.repository.DataStoreOperations
import com.example.attackontitanapp.domain.repository.LocalDataSource
import com.example.attackontitanapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllTitans(): Flow<PagingData<Titan>> {
        return remote.getAllTitans()
    }

    fun searchTitans(query: String): Flow<PagingData<Titan>> {
        return remote.searchTitans(query = query)
    }

    suspend fun getSelectedTitan(titanId: Int): Titan {
        return local.getSelectedTitan(heroId = titanId)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}