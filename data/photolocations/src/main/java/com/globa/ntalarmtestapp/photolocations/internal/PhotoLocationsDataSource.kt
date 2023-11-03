package com.globa.ntalarmtestapp.photolocations.internal

import com.globa.ntalarmtestapp.common.di.IoDispatcher
import com.globa.ntalarmtestapp.database.api.PhotosDatabase
import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel
import com.globa.ntalarmtestapp.photolocations.api.PhotoLocation
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
internal class PhotoLocationsLocalDataSource @Inject constructor(
    private val database: PhotosDatabase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend fun getPhotoLocations() = withContext(coroutineDispatcher) {
        database.photosDao.getPhotos().map { list -> list.map { it.asPhotoLocationModel() } }
    }
}

fun PhotoDBModel.asPhotoLocationModel() =
    PhotoLocation(
        id = this.id,
        latitude = this.latitude,
        longitude = this.longitude
    )