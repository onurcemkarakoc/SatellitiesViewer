package com.onurcemkarakoc.feature.satellities_list.domain.use_cases

import com.onurcemkarakoc.core.common.utils.Resource
import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(private val satelliteDataProvider: SatelliteDataProvider) {
    suspend operator fun invoke(query:String) = flow {
        emit(Resource.Loading())

        val result = satelliteDataProvider.getSatelliteList(query)
        emit(Resource.Success(result))

    }.catch {
        emit(Resource.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}