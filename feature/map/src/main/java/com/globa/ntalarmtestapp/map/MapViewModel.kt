package com.globa.ntalarmtestapp.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.ntalarmtestapp.photolocations.api.PhotoLocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: PhotoLocationsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchPhotoLocations() {
        viewModelScope.launch {
            repository.getPhotoLocations()
                .collect {list ->
                    _uiState.update { MapUiState(photos = list) }
                }
        }
    }
}