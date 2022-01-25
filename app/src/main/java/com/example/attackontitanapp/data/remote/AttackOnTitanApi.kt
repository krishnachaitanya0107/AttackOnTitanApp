package com.example.attackontitanapp.data.remote

import com.example.attackontitanapp.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AttackOnTitanApi {

    @GET("/titans")
    suspend fun getAllTitans(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/titans/search")
    suspend fun searchTitans(
        @Query("name") name: String
    ): ApiResponse

}