package com.example.samplemvvmapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.network.RetroApi
import com.example.samplemvvmapplication.utils.Constants
import com.example.samplemvvmapplication.utils.Constants.error_login
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepository @Inject constructor(
    val api : RetroApi
) {
    private val _login = MutableLiveData<Response<JsonElement>>()
    val login: LiveData<Response<JsonElement>>
        get() = _login

    suspend fun getLoginResponse(json: JsonObject?) {
        json?.let {
            Log.d("response header", it.toString())
            _login.postValue(Response.Loading())
            try {
                val res = api.getlogin(it)
                if (res.isSuccessful)
                    _login.postValue(Response.Success(res.body()))
                else {
                    res.errorBody()?.let { err ->
                        _login.postValue(
                            Response.Error(error_login,res.code())
                        )
                    }
                }

            } catch (httpEx: HttpException) {
                _login.postValue(Response.Error(Constants.HTTP_ERROR, -1))

            } catch (e: Exception) {
                Log.d("error", e.toString())
                _login.postValue(Response.Error(Constants.SERVER_ERROR, -1))
            }

        }

    }
}