package com.globa.ntalarmtestapp.photodetails.api

import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel

data class PhotoDetails(
    val id: Int = 0,
    val path: String,
    val title: String,
    val date: Long,
    val location: Location
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

fun PhotoDBModel.asDetailDomainModel() =
    PhotoDetails(
        id = this.id,
        path = this.path,
        title = this.photoTitle,
        date = this.date,
        location = Location(latitude = this.latitude, longitude = this.longitude)
    )

fun PhotoDetails.asDBModel() =
    PhotoDBModel(
        id = this.id,
        path = this.path,
        photoTitle = this.title,
        date = this.date,
        latitude = this.location.latitude,
        longitude = this.location.longitude
    )
