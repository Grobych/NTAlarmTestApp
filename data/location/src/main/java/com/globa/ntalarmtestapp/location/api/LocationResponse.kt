package com.globa.ntalarmtestapp.location.api

import android.location.Location

sealed class LocationResponse {
    data class Success(val location: Location): LocationResponse()
    data class Error(val message: String): LocationResponse()
}