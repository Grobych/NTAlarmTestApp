package com.globa.ntalarmtestapp.map

import com.globa.ntalarmtestapp.photolocations.api.PhotoLocation

data class MapUiState (
    val photos: List<PhotoLocation> = emptyList()
)
