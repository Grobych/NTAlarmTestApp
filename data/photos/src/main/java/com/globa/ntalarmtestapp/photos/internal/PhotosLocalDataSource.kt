package com.globa.ntalarmtestapp.photos.internal

import com.globa.ntalarmtestapp.common.di.IoDispatcher
import com.globa.ntalarmtestapp.database.api.PhotosDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosLocalDataSource @Inject constructor(
    private val db: PhotosDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getPhotos() = withContext(dispatcher) {
        db.photosDao.getPhotos()
    }
}