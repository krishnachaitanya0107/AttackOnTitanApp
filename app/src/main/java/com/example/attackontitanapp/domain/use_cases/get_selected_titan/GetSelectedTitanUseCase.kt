package com.example.attackontitanapp.domain.use_cases.get_selected_titan

import com.example.attackontitanapp.data.repository.Repository
import com.example.attackontitanapp.domain.model.Titan

class GetSelectedTitanUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Titan {
        return repository.getSelectedTitan(titanId = heroId)
    }
}