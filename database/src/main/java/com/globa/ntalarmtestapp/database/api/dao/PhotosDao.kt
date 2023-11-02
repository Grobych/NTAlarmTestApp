package com.globa.ntalarmtestapp.database.api.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globa.ntalarmtestapp.database.api.model.PhotoDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Query("select * from photos order by date asc")
    fun getPhotos(): Flow<List<PhotoDBModel>>
    @Query("select * from photos where id = :id")
    fun getPhotoById(id: Int): Flow<PhotoDBModel>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: PhotoDBModel)
    @Delete
    fun removePhoto(photo: PhotoDBModel)
}