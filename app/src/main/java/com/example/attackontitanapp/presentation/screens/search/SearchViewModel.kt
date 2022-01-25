package com.example.attackontitanapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.attackontitanapp.domain.model.Titan
import com.example.attackontitanapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedTitans = MutableStateFlow<PagingData<Titan>>(PagingData.empty())
    val searchedTitans = _searchedTitans

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchTitans(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchTitansUseCase(query = query).cachedIn(viewModelScope).collect {
                _searchedTitans.value = it
            }
        }
    }

}