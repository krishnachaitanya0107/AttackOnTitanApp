package com.example.attackontitanapp.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.attackontitanapp.data.remote.AttackOnTitanApi
import com.example.attackontitanapp.domain.model.Titan
import java.lang.Exception

class SearchTitansSource(
    private val attackOnTitanApi: AttackOnTitanApi,
    private val query: String
) : PagingSource<Int, Titan>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Titan> {
        return try {
            val apiResponse = attackOnTitanApi.searchTitans(name = query)
            val heroes = apiResponse.titans
            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Titan>): Int? {
        return state.anchorPosition
    }
}