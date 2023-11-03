package com.globa.ntalarmtestapp.photolocations.api

import kotlinx.coroutines.flow.Flow

interface PhotoLocationsRepository {
    suspend fun getPhotoLocations(): Flow<List<PhotoLocation>>
}