package com.globa.ntalarmtestapp.photodetails.internal

import com.globa.ntalarmtestapp.photodetails.api.PhotoDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal interface PhotoDetailsRepositoryBinding {
    @Binds
    fun bingPhotoDetailsRepository(impl: PhotoDetailsRepositoryImpl): PhotoDetailsRepository
}