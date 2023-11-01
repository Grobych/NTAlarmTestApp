package com.globa.ntalarmtestapp.database.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "photo_title") val photoTitle: String,
    val path: String,
    val date: String,
    val latitude: Double,
    val longitude: Double
)
