package com.globa.ntalarmtestapp.photodetails.internal

import com.globa.ntalarmtestapp.common.di.IoDispatcher
import com.globa.ntalarmtestapp.database.api.PhotosDatabase
import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PhotoDetailsLocalDataSource @Inject constructor(
    private val db: PhotosDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun load(id: Int) = withContext(dispatcher) {
        db.photosDao.getPhotoById(id = id)
    }

    suspend fun save(photo: PhotoDBModel) = withContext(dispatcher) {
        db.photosDao.insertPhoto(photo)
    }

    suspend fun remove(photo: PhotoDBModel) = withContext(dispatcher) {
        db.photosDao.removePhoto(photo)
    }
}