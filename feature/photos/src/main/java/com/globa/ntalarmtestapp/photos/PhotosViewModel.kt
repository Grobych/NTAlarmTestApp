package com.globa.ntalarmtestapp.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.ntalarmtestapp.photos.api.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
): ViewModel() {
    private val _photosUiState = MutableStateFlow<PhotosUiState>(PhotosUiState.Loading)
    val photosUiState = _photosUiState.asStateFlow()

    private val _searchDate = MutableStateFlow<Long?>(null)

    fun viewModelInit() {
        dateFlowInit()
    }
    init {
        viewModelInit()
    }

    private fun fetchPhotos(date: Long?) {
        viewModelScope.launch {
            photosRepository.getPhotos()
                .map { photos -> // alternative way - filter in sql request
                    photos.filter {
                        if (date == null) true
                        else {
                            val delta = it.date*1000 - date
                            if (delta < 0) false
                            else delta < TimeUnit.DAYS.toMillis(1)
                        }
                    }
                }
                .collect {
                    _photosUiState.value = PhotosUiState.Done(it, date)
                }
        }
    }

    fun onDateChanged(date: Long?) {
        _searchDate.value = date
    }

    private fun dateFlowInit() {
        viewModelScope.launch {
            _searchDate
                .onEach {
                    fetchPhotos(it)
                }
                .collect()
        }
    }
}