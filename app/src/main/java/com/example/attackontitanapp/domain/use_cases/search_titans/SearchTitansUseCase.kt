package com.example.attackontitanapp.domain.use_cases.search_titans

import androidx.paging.PagingData
import com.example.attackontitanapp.data.repository.Repository
import com.example.attackontitanapp.domain.model.Titan
import kotlinx.coroutines.flow.Flow

class SearchTitansUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Titan>> {
        return repository.searchTitans(query = query)
    }
}