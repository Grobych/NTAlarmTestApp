package com.globa.ntalarmtestapp.database.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_remote_keys")
data class PhotoRemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "photo_id") val photoId: Int,
    @ColumnInfo(name = "previous_key") val prevKey: Int?,
    @ColumnInfo(name = "next_key") val nextKey: Int?
)