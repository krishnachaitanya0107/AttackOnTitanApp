package com.example.attackontitanapp.domain.use_cases

import com.example.attackontitanapp.domain.use_cases.get_all_titans.GetAllTitansUseCase
import com.example.attackontitanapp.domain.use_cases.get_selected_titan.GetSelectedTitanUseCase
import com.example.attackontitanapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.attackontitanapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.attackontitanapp.domain.use_cases.search_titans.SearchTitansUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllTitansUseCase: GetAllTitansUseCase,
    val searchTitansUseCase: SearchTitansUseCase,
    val getSelectedTitanUseCase: GetSelectedTitanUseCase
)