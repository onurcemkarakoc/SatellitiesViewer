package com.onurcemkarakoc.feature.satellite_detail.domain.di

import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import com.onurcemkarakoc.feature.satellite_detail.domain.use_cases.GetSatelliteDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainLayerModule {
    @Provides
    @Singleton
    fun provideGetSatelliteDetailUseCase(satelliteDataProvider: SatelliteDataProvider): GetSatelliteDetailUseCase =
        GetSatelliteDetailUseCase(satelliteDataProvider)
}