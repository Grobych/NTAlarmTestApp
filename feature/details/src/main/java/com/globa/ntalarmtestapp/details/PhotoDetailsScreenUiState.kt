package com.globa.ntalarmtestapp.details

sealed class PhotoDetailsScreenUiState {
    object Loading: PhotoDetailsScreenUiState()
    data class Done(
        val imageTitle: String,
        val imageUri: String,
        val date: String,
        val location: String
    ): PhotoDetailsScreenUiState()
    object Deleted: PhotoDetailsScreenUiState()
    data class Error(val message: String): PhotoDetailsScreenUiState()
}
