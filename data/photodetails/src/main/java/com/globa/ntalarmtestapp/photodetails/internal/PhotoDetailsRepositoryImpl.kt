package com.globa.ntalarmtestapp.photodetails.internal

import com.globa.ntalarmtestapp.photodetails.api.PhotoDetails
import com.globa.ntalarmtestapp.photodetails.api.PhotoDetailsRepository
import com.globa.ntalarmtestapp.photodetails.api.asDBModel
import com.globa.ntalarmtestapp.photodetails.api.asDetailDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PhotoDetailsRepositoryImpl @Inject constructor(
    private val dataSource: PhotoDetailsLocalDataSource
): PhotoDetailsRepository {
    override suspend fun getPhoto(id: Int): Flow<PhotoDetails> =
        dataSource.load(id).map { it.asDetailDomainModel() }


    override suspend fun savePhoto(photoDetails: PhotoDetails) =
        dataSource.save(photoDetails.asDBModel())

    override suspend fun removePhoto(photoDetails: PhotoDetails) =
        dataSource.remove(photoDetails.asDBModel())
}