package com.globa.ntalarmtestapp.location.internal

import com.globa.ntalarmtestapp.location.api.LocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource
): LocationRepository {
    override suspend fun getLocation() = locationDataSource.getCurrentLocation()
}