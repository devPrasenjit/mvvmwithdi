package com.example.samplemvvmapplication.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repository: DetailsRepository
) : ViewModel() {

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")
//    val details: LiveData<Response<Data>>
//        get() = repository.details
//
//
//    fun getDetails(id : Int){
//        viewModelScope.launch {
//            repository.getUserDetails(id)
//        }
//    }
}