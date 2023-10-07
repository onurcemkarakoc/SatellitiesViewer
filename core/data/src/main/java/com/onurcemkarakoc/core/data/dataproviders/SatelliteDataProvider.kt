package com.onurcemkarakoc.core.data.dataproviders

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.onurcemkarakoc.core.common.Constants
import com.onurcemkarakoc.core.common.utils.readJsonAsset
import com.onurcemkarakoc.core.data.model.Position
import com.onurcemkarakoc.core.data.model.PositionItem
import com.onurcemkarakoc.core.data.model.Satellite
import com.onurcemkarakoc.core.data.model.SatelliteDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SatelliteDataProvider(
    private val context: Context,
    private val gson: Gson,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun getSatelliteList(query: String): List<Satellite> {
        val jsonString = context.readJsonAsset(Constants.SATELLITES_JSON_FILE_NAME)
        delay(1000) // fake delay
        val list = gson.fromJson(jsonString, Array<Satellite>::class.java).toList()
        return list.filter {
            it.name.contains(query, true)
        }
    }

    suspend fun getSatelliteDetail(id: String): SatelliteDetail? {

        val hasCache = dataStore.data.map {
            it.contains(stringPreferencesKey(id))
        }.firstOrNull() ?: false

        if (hasCache) {
            val cache = dataStore.data.map {
                it[stringPreferencesKey(id)]
            }
            return cache.firstOrNull()?.let {
                gson.fromJson(it, SatelliteDetail::class.java)
            }
        }

        val jsonString= context.readJsonAsset(Constants.SATELLITE_DETAIL_JSON_FILE_NAME)
        delay(1000) // fake delay
        val satelliteDetail = gson.fromJson(jsonString, Array<SatelliteDetail>::class.java).toList()
            .find { it.id.toString() == id }
        dataStore.edit {
            it[stringPreferencesKey(id)] = gson.toJson(satelliteDetail)
        }
        return satelliteDetail
    }

    fun getSatellitePositions(id: String): PositionItem? {
        val jsonString = context.readJsonAsset(Constants.POSITIONS_JSON_FILE_NAME)
        val position = gson.fromJson(jsonString, Position::class.java)
        return position.list.find { it.id == id }
    }
}