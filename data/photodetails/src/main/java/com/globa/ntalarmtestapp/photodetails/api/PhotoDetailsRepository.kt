package com.globa.ntalarmtestapp.photodetails.api

import kotlinx.coroutines.flow.Flow

interface PhotoDetailsRepository {
    suspend fun getPhoto(id: Int): Flow<PhotoDetails>
    suspend fun savePhoto(photoDetails: PhotoDetails)
    suspend fun removePhoto(photoDetails: PhotoDetails)
}