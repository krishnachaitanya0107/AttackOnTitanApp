package com.example.attackontitanapp.data.repository

import com.example.attackontitanapp.data.local.AotDatabase
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(aotDatabase: AotDatabase): LocalDataSource {

    private val titanDao = aotDatabase.titanDao()

    override suspend fun getSelectedTitan(heroId: Int): Titan {
        return titanDao.getSelectedTitan(heroId = heroId)
    }
}