package com.globa.ntararmtestapp.camera

import android.graphics.Bitmap
import android.net.Uri

sealed class CameraScreenUiState {
    object Creating: CameraScreenUiState()
    data class Processing(val stateMessage: String): CameraScreenUiState()
    data class ReadyToSave(
        val path: Uri?,
        val bitmap: Bitmap,
        val location: String,
        val name: String,
        val date: String
    ): CameraScreenUiState()
    object Saving: CameraScreenUiState()
    object Done: CameraScreenUiState()
    data class Error(val message: String): CameraScreenUiState()
}
