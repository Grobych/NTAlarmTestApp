package com.globa.ntalarmtestapp.photos.internal

import com.globa.ntalarmtestapp.photos.api.PhotosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PhotoRepositoryBinding {
    @Binds
    fun bingPhotoRepository(impl: PhotosRepositoryImpl): PhotosRepository
}