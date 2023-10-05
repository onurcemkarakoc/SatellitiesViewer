package com.onurcemkarakoc.feature.satellities_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.data.model.Satellite
import com.onurcemkarakoc.feature.satellities_list.domain.use_cases.GetSatelliteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitiesListViewModel @Inject constructor(private val getSatelliteListUseCase: GetSatelliteListUseCase) :
    ViewModel() {

    private val _satellitiesList = MutableLiveData<List<Satellite>>()
    val satellitiesList: LiveData<List<Satellite>> = _satellitiesList

    fun getSatelliteList() {
        viewModelScope.launch {
            getSatelliteListUseCase().collectLatest { resource ->
                resource.data?.let {
                    _satellitiesList.postValue(it)
                }
            }
        }
    }
}