package com.waynebloom.fetchingfromfetch.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waynebloom.fetchingfromfetch.dagger.factory.MutableStateFlowFactory
import com.waynebloom.fetchingfromfetch.network.domain.model.Product
import com.waynebloom.fetchingfromfetch.network.domain.usecase.GetData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getData: GetData,
    mutableStateFlowFactory: MutableStateFlowFactory,
): ViewModel() {

    private val viewModelState: MutableStateFlow<HomeState>
    val uiState: StateFlow<HomeUiState>

    init {
        viewModelState = mutableStateFlowFactory.newInstance(HomeState())
        uiState = viewModelState
            .map(HomeState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = viewModelState.value.toUiState())

        viewModelScope.launch {
            getData().collectLatest { data ->
                viewModelState.update {
                    it.copy(loading = false, data = data)
                }
            }
        }

    }

    data class HomeState(
        val loading: Boolean = true,
        val data: Map<Int, List<Product>> = mapOf()
    ) {

        fun toUiState() = if (loading) {
            HomeUiState.Loading
        } else {
            HomeUiState.Content(data)
        }
    }

    sealed interface HomeUiState {
        data object Loading: HomeUiState
        data class Content(
            val data: Map<Int, List<Product>>,
        ): HomeUiState
    }
}