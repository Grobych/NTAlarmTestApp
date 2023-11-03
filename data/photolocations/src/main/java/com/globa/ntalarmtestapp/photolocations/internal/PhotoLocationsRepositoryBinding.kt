package com.globa.ntalarmtestapp.photolocations.internal

import com.globa.ntalarmtestapp.photolocations.api.PhotoLocationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface PhotoLocationsRepositoryBinding {
    @Binds
    fun bindPhotoLocationsRepository(impl: PhotoLocationsRepositoryImpl): PhotoLocationsRepository
}