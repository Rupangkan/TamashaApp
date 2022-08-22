package com.example.tamashaapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.tamashaapp.common.Constants
import com.example.tamashaapp.common.Resource
import com.example.tamashaapp.model.Data
import com.example.tamashaapp.model.Employees
import com.example.tamashaapp.network.RetroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val retroRepository: RetroRepository
): ViewModel() {
    private val liveDataList: MutableLiveData<Employees> = MutableLiveData(Employees())

    fun dataList(): LiveData<Employees> {
        return liveDataList
    }

    init {
        loadListData()
    }

    fun loadListData() {
        retroRepository.makeApiCall().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    liveDataList.value?.loading = false
                    liveDataList.value = result.data!!
                    Log.d("ViewModel", "loadData: ${result.data}")
                }
                is Resource.Error -> {
                    liveDataList.value = Employees(
                        data = emptyList(),
                        message = result.message!!,
                        status = Constants.FAILURE,
                        loading = false
                    )
                    Log.d("Error", result.message)
                }
                is Resource.Loading -> {
                    liveDataList.value?.loading = true
                    Log.d("Loading", "Loading")
                }
            }
        }.launchIn(viewModelScope)

    }

}