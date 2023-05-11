package com.example.samplemvvmapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.network.RetroApi
import com.example.samplemvvmapplication.data.paging.UserlistPagingSource
import com.example.samplemvvmapplication.utils.Constants.TOTAL_NO_OF_PRODUCTS_PER_PAGE
import javax.inject.Inject

class Dashboardrepository @Inject constructor(
    val api : RetroApi
) {


    val _currentQuery = MutableLiveData<Int>()

    fun getData(): LiveData<PagingData<Data>> =
        _currentQuery.switchMap { query->
            Pager(
                config = PagingConfig(pageSize = TOTAL_NO_OF_PRODUCTS_PER_PAGE),
                pagingSourceFactory = { UserlistPagingSource(api,query) }
            ).liveData
        }
}