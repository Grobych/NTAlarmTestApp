package com.globa.ntalarmtestapp.photos

import com.globa.ntalarmtestapp.photos.api.Photo

sealed class PhotosUiState {
    object Loading: PhotosUiState()
    data class Done(val photos: List<Photo>): PhotosUiState()
    data class Error(val message: String): PhotosUiState()
}