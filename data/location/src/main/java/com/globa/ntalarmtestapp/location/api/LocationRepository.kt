package com.globa.ntalarmtestapp.location.api

interface LocationRepository {
    suspend fun getLocation(): LocationResponse
}