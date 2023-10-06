package com.onurcemkarakoc.feature.satellite_detail.domain.use_cases

import com.onurcemkarakoc.core.common.utils.Resource
import com.onurcemkarakoc.core.data.dataproviders.SatelliteDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSatellitePositionsUseCase @Inject constructor(private val satelliteDataProvider: SatelliteDataProvider) {
    suspend operator fun invoke(id: String) = flow {
        emit(Resource.Loading())
        val result = satelliteDataProvider.getSatellitePositions(id)
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}