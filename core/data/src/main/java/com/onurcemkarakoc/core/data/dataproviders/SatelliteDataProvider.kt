package com.onurcemkarakoc.core.data.dataproviders

import com.google.gson.Gson
import com.onurcemkarakoc.core.data.model.Satellite
import com.onurcemkarakoc.core.data.model.SatelliteDetail
import kotlinx.coroutines.delay

class SatelliteDataProvider(private val gson: Gson) {
    suspend fun getSatelliteList(jsonString: String): List<Satellite> {
        delay(2000) // fake delay
        return gson.fromJson(jsonString, Array<Satellite>::class.java).toList()
    }

    suspend fun getSatelliteDetail(jsonString: String,id:String) {
        delay(2000) // fake delay
        gson.fromJson(jsonString, Array<SatelliteDetail>::class.java).toList().find { it.id.toString() == id }
    }
}