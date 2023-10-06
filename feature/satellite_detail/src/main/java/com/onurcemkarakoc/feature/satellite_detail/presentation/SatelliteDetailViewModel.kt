package com.onurcemkarakoc.feature.satellite_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.common.base.BaseViewModel
import com.onurcemkarakoc.core.data.model.PositionItem
import com.onurcemkarakoc.core.data.model.SatelliteDetail
import com.onurcemkarakoc.feature.satellite_detail.domain.use_cases.GetSatelliteDetailUseCase
import com.onurcemkarakoc.feature.satellite_detail.domain.use_cases.GetSatellitePositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) :
    BaseViewModel() {

    private val _satelliteDetail = MutableLiveData<SatelliteDetail>()
    val satelliteDetail: LiveData<SatelliteDetail> = _satelliteDetail

    private val _satellitePositionItem = MutableLiveData<PositionItem>()
    val satellitePositionItem: LiveData<PositionItem> = _satellitePositionItem

    fun getSatelliteDetail(satelliteId: String) {
        viewModelScope.launch {
            getSatelliteDetailUseCase(satelliteId).collectLatest { resource ->
                handleResource(resource) { response ->
                    response?.let { safeResponse ->
                        _satelliteDetail.postValue(safeResponse)
                    }
                }
            }
        }
    }
    fun getSatellitePositionItem(satelliteId: String) {
        viewModelScope.launch {
            getSatellitePositionsUseCase(satelliteId).collectLatest { resource ->
                handleResource(resource) { response ->
                    response?.let { safeResponse ->
                        _satellitePositionItem.postValue(safeResponse)
                    }
                }
            }
        }
    }

}