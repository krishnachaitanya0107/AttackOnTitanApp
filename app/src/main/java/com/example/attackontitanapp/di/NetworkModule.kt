package com.example.attackontitanapp.di

import androidx.paging.ExperimentalPagingApi
import com.example.attackontitanapp.data.local.AotDatabase
import com.example.attackontitanapp.data.remote.AttackOnTitanApi
import com.example.attackontitanapp.data.repository.RemoteDataSourceImpl
import com.example.attackontitanapp.domain.repository.RemoteDataSource
import com.example.attackontitanapp.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideAttackOnTitanApi(retrofit: Retrofit): AttackOnTitanApi {
        return retrofit.create(AttackOnTitanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        attackOnTitanApi: AttackOnTitanApi,
        aotDatabase: AotDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            attackOnTitanApi = attackOnTitanApi,
            aotDatabase = aotDatabase
        )
    }

}