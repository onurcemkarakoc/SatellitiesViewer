package com.onurcemkarakoc.core.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onurcemkarakoc.core.common.utils.Resource
import com.onurcemkarakoc.core.common.utils.State

open class BaseViewModel : ViewModel() {

    val state: MutableLiveData<State?> = MutableLiveData()
    fun <T> handleResource(resource: Resource<T>, successResultCallback: (T?) -> Unit) {
        when (resource) {
            is Resource.Error -> state.postValue(State.error(resource.message))
            is Resource.Loading -> state.postValue(State.loading())
            is Resource.Success -> {
                state.postValue(State.success())
                successResultCallback.invoke(resource.data)
            }
        }
    }

}