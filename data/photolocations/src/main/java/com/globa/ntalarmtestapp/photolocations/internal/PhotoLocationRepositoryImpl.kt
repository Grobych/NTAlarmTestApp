package com.globa.ntalarmtestapp.photolocations.internal

import com.globa.ntalarmtestapp.photolocations.api.PhotoLocation
import com.globa.ntalarmtestapp.photolocations.api.PhotoLocationsRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
internal class PhotoLocationsRepositoryImpl @Inject constructor(
    private val dataSource: PhotoLocationsLocalDataSource
): PhotoLocationsRepository {
    override suspend fun getPhotoLocations(): Flow<List<PhotoLocation>> = dataSource.getPhotoLocations()
}