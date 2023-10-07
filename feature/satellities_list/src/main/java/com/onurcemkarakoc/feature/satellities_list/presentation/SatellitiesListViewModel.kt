@file:OptIn(ExperimentalCoroutinesApi::class)

package com.onurcemkarakoc.feature.satellities_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onurcemkarakoc.core.common.base.BaseViewModel
import com.onurcemkarakoc.core.data.model.Satellite
import com.onurcemkarakoc.feature.satellities_list.domain.use_cases.GetSatelliteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SatellitiesListViewModel @Inject constructor(private val getSatelliteListUseCase: GetSatelliteListUseCase) :
    BaseViewModel() {

    private val _satellitiesList = MutableLiveData<List<Satellite>>()
    val satellitiesList: LiveData<List<Satellite>> = _satellitiesList

    var queryFlow = MutableStateFlow("")

    init {
        query(queryFlow)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun query(queryFlow: Flow<String>) {
        viewModelScope.launch {
            queryFlow.debounce(DEBOUNCE_TIME)
                .flatMapLatest {
                    getSatelliteListUseCase.invoke(it)
                }.collectLatest {
                    handleResource(it) { response ->
                        response?.let { safeResponse ->
                            _satellitiesList.postValue(safeResponse)
                        }
                    }
                }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 500L
    }
}