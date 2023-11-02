package com.globa.ntalarmtestapp.photos.api

import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(): Flow<List<Photo>>
}