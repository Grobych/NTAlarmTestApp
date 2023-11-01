package com.globa.ntalarmtestapp.database.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globa.ntalarmtestapp.database.api.dao.PhotosDao
import com.globa.ntalarmtestapp.database.api.dao.PhotosRemoteKeyDao
import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel
import com.globa.ntalarmtestapp.database.api.model.PhotoRemoteKey

@Database(
    entities = [PhotoDBModel::class, PhotoRemoteKey::class],
    version = 1,
    exportSchema = false
)

abstract class PhotosDatabase : RoomDatabase() {
    abstract val photosDao: PhotosDao
    abstract val photosRemoteKeyDao: PhotosRemoteKeyDao
}