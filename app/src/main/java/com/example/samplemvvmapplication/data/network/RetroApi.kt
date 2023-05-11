package com.example.samplemvvmapplication.data.network

import com.example.samplemvvmapplication.data.model.DashboardModel
import com.example.samplemvvmapplication.data.model.Data
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface RetroApi {

    @POST("login")
    suspend fun getlogin(@Body loginobject: JsonObject): Response<JsonElement>

    @GET("users?")
    suspend fun getUserList(@Query("page") page : Int): Response<DashboardModel>

    @GET("users/")
    suspend fun getUserDetails(@Url page : Int): Response<Data>
}