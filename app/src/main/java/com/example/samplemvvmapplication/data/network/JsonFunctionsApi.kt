package com.example.samplemvvmapplication.data.network

import com.google.gson.JsonObject
import javax.inject.Inject

class JsonFunctionsApi @Inject constructor(){

    fun login(email: String?, pass: String?): JsonObject {
        var obj = JsonObject()
        obj.addProperty("email",email)
        obj.addProperty("password",pass)
        return obj
    }
}