package com.globa.ntalarmtestapp.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.ntalarmtestapp.photos.api.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
): ViewModel() {
    private val _photosUiState = MutableStateFlow<PhotosUiState>(PhotosUiState.Loading)
    val photosUiState = _photosUiState.asStateFlow()
    init {
        fetchPhotos()
    }

    fun fetchPhotos() {
        viewModelScope.launch {
            photosRepository.getPhotos()
                .collect {
                    _photosUiState.value = PhotosUiState.Done(it)
                }
        }
    }
}