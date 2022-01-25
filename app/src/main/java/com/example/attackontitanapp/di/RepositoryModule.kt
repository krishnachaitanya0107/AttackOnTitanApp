package com.example.attackontitanapp.di

import android.content.Context
import com.example.attackontitanapp.data.repository.DataStoreOperationsImpl
import com.example.attackontitanapp.data.repository.Repository
import com.example.attackontitanapp.domain.repository.DataStoreOperations
import com.example.attackontitanapp.domain.use_cases.UseCases
import com.example.attackontitanapp.domain.use_cases.get_all_titans.GetAllTitansUseCase
import com.example.attackontitanapp.domain.use_cases.get_selected_titan.GetSelectedTitanUseCase
import com.example.attackontitanapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.attackontitanapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.attackontitanapp.domain.use_cases.search_titans.SearchTitansUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllTitansUseCase = GetAllTitansUseCase(repository),
            searchTitansUseCase = SearchTitansUseCase(repository),
            getSelectedTitanUseCase = GetSelectedTitanUseCase(repository)
        )
    }

}