package com.example.samplemvvmapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.network.RetroApi
import com.example.samplemvvmapplication.utils.Constants
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.HttpException
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    val api: RetroApi
) {
    private val _details = MutableLiveData<Response<Data>>()
    val details: LiveData<Response<Data>>
        get() = _details

    suspend fun getUserDetails(id : Int?) {
        id?.let {
            Log.d("response header", it.toString())
            _details.postValue(Response.Loading())
            try {
                val res = api.getUserDetails(it)
                if (res.isSuccessful)
                    _details.postValue(Response.Success(res.body()))
                else {
                    res.errorBody()?.let { err ->
                        _details.postValue(
                            Response.Error("",res.code())
                        )
                    }
                }

            } catch (httpEx: HttpException) {
                _details.postValue(Response.Error(Constants.HTTP_ERROR, -1))

            } catch (e: Exception) {
                Log.d("error", e.toString())
                _details.postValue(Response.Error(Constants.SERVER_ERROR, -1))
            }

        }

    }
}