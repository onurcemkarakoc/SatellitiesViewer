package com.onurcemkarakoc.core.data.di

import com.google.gson.Gson
import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLayerModule {
    @Provides
    @Singleton
    fun provideSatelliteDataProvider(gson: Gson): SatelliteDataProvider = SatelliteDataProvider(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}