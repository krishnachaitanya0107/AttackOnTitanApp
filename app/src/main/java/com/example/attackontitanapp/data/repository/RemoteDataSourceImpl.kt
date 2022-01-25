package com.example.attackontitanapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.attackontitanapp.data.local.AotDatabase
import com.example.attackontitanapp.data.paging_source.TitanRemoteMediator
import com.example.attackontitanapp.data.paging_source.SearchTitansSource
import com.example.attackontitanapp.data.remote.AttackOnTitanApi
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.repository.RemoteDataSource
import com.example.attackontitanapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val attackOnTitanApi: AttackOnTitanApi,
    private val aotDatabase: AotDatabase
) : RemoteDataSource {

    private val titanDao = aotDatabase.titanDao()

    override fun getAllTitans(): Flow<PagingData<Titan>> {
        val pagingSourceFactory = { titanDao.getAllTitans() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TitanRemoteMediator(
                attackOnTitanApi =  attackOnTitanApi,
                aotDatabase = aotDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchTitans(query: String): Flow<PagingData<Titan>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchTitansSource(attackOnTitanApi = attackOnTitanApi, query = query)
            }
        ).flow
    }
}