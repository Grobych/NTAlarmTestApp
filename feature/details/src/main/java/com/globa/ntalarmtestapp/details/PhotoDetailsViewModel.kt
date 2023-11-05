package com.globa.ntalarmtestapp.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.ntalarmtestapp.common.util.DateFormatter
import com.globa.ntalarmtestapp.photodetails.api.PhotoDetails
import com.globa.ntalarmtestapp.photodetails.api.PhotoDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PhotoDetailsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<PhotoDetailsScreenUiState>(PhotoDetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val photoId = savedStateHandle.get<Int>("photoId")
    private val _photo = MutableStateFlow<PhotoDetails?>(null)

    private val _showRemoveDialog = MutableStateFlow(false)
    val showRemoveDialog = _showRemoveDialog.asStateFlow()

    init {
        loadPhoto()
    }
    private fun loadPhoto() {
        if (photoId == null) _uiState.value = PhotoDetailsScreenUiState.Error("Incorrect Id!")
        else {
            viewModelScope.launch {
                repository.getPhoto(photoId)
                    .catch {  }
                    .collect {
                        _photo.value = it
                        _uiState.value =
                            PhotoDetailsScreenUiState.Done(
                                imageTitle = it.title,
                                imageUri = it.path,
                                date = DateFormatter.getExtendDate(it.date),
                                location = "Lat: ${it.location.latitude}; Long: ${it.location.longitude}."
                            )
                    }
            }
        }
    }

    fun onRemoveButtonCLick() {
        _showRemoveDialog.value = true
    }

    fun onRemoveDecline() {
        _showRemoveDialog.value = false
    }

    fun onRemoveAccept() {
        viewModelScope.launch {
            if (_photo.value != null) {
                _uiState.value = PhotoDetailsScreenUiState.Loading
                repository.removePhoto(_photo.value!!)
                _uiState.value = PhotoDetailsScreenUiState.Deleted
            }
        }
    }
}