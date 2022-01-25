package com.example.attackontitanapp.domain.use_cases.get_all_titans

import androidx.paging.PagingData
import com.example.attackontitanapp.data.repository.Repository
import com.example.attackontitanapp.domain.model.Titan
import kotlinx.coroutines.flow.Flow

class GetAllTitansUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Titan>> {
        return repository.getAllTitans()
    }
}