package com.onurcemkarakoc.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.satelliteDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.onurcemkarakoc.core.data"
)
@Module
@InstallIn(SingletonComponent::class)
object DataLayerModule {
    @Provides
    @Singleton
    fun provideSatelliteDataProvider(
        @ApplicationContext applicationContext: Context,
        gson: Gson,
        dataStore: DataStore<Preferences>
    ): SatelliteDataProvider = SatelliteDataProvider(applicationContext, gson, dataStore)

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideUserDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.satelliteDataStore
    }
}