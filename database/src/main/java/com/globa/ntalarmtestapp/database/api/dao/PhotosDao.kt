package com.globa.ntalarmtestapp.database.api.dao

import androidx.paging.PagingSource
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
    fun getPhotos(): PagingSource<Int, PhotoDBModel>
    @Query("select * from photos where id = :id")
    fun getPhotoById(id: Int): Flow<PhotoDBModel>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photo : List<PhotoDBModel>)
    @Delete
    fun removePhoto(photo: PhotoDBModel)
    @Query("DELETE FROM photos")
    fun clearAll()
    @Query("select MAX(date) from photos")
    fun getLastUpdated(): Long
}