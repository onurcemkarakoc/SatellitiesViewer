package com.onurcemkarakoc.feature.satellities_list.domain.di

import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import com.onurcemkarakoc.feature.satellities_list.domain.use_cases.GetSatellitiesListUseCase
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
    fun provideGetSatellitiesUseCase(satelliteDataProvider: SatelliteDataProvider): GetSatellitiesListUseCase =
        GetSatellitiesListUseCase(satelliteDataProvider)
}