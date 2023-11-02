package com.globa.ntalarmtestapp.photos.internal

import com.globa.ntalarmtestapp.photos.api.PhotosRepository
import com.globa.ntalarmtestapp.photos.api.asDomainModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val dataSource: PhotosLocalDataSource
): PhotosRepository {
    override suspend fun getPhotos() = dataSource.getPhotos().map { dbModels ->
        dbModels.map {
            it.asDomainModel()
        }
    }
}