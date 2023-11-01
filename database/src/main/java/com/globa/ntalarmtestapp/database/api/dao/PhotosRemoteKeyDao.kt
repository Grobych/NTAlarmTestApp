package com.globa.ntalarmtestapp.database.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.ntalarmtestapp.database.api.model.PhotoRemoteKey

@Dao
interface PhotosRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<PhotoRemoteKey>)
    @Query("SELECT * FROM photos_remote_keys WHERE photo_id = :photoId")
    fun getRemoteKeyByPhotoId(photoId: Int): PhotoRemoteKey?
    @Query("DELETE FROM photos_remote_keys")
    fun clearAll()
}