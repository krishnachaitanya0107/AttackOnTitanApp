package com.example.attackontitanapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.attackontitanapp.data.local.AotDatabase
import com.example.attackontitanapp.data.remote.AttackOnTitanApi
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.model.TitanRemoteKeys

@ExperimentalPagingApi
class TitanRemoteMediator (
    private val attackOnTitanApi: AttackOnTitanApi,
    private val aotDatabase: AotDatabase
) : RemoteMediator<Int, Titan>() {

    private val titanDao = aotDatabase.titanDao()
    private val titanRemoteKeysDao = aotDatabase.titanRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = titanRemoteKeysDao.getRemoteKeys(titanId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Titan>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = attackOnTitanApi.getAllTitans(page = page)
            if (response.titans.isNotEmpty()) {
                aotDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        titanDao.deleteAllTitans()
                        titanRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.titans.map { titan ->
                        TitanRemoteKeys(
                            id = titan.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    titanRemoteKeysDao.addAllRemoteKeys(titanRemoteKeys = keys)
                    titanDao.addTitans(titans = response.titans)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Titan>
    ): TitanRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                titanRemoteKeysDao.getRemoteKeys(titanId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Titan>
    ): TitanRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { titan ->
                titanRemoteKeysDao.getRemoteKeys(titanId = titan.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Titan>
    ): TitanRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { titan ->
                titanRemoteKeysDao.getRemoteKeys(titanId = titan.id)
            }
    }

}