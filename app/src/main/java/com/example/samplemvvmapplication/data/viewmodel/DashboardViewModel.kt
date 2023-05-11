package com.example.samplemvvmapplication.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.samplemvvmapplication.data.model.DashboardModel
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.repository.Dashboardrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Dashboardrepository,
) : ViewModel() {

    init {

    }

    var data_list : LiveData<PagingData<Data>> = MutableLiveData()

    fun getUserList() {
        repository._currentQuery.value = 1
        data_list = repository.getData().cachedIn(viewModelScope)
    }
}