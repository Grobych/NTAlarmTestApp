package com.globa.ntalarmtestapp.photos.api

import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel

data class Photo(
    val id: Int,
    val path: String,
    val date: Long
)

fun PhotoDBModel.asDomainModel() = Photo(
    id = this.id,
    path = this.path,
    date = this.date
)
