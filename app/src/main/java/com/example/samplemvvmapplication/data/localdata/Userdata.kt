package com.example.samplemvvmapplication.data.localdata

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Userdata@Inject constructor(@ApplicationContext private val context: Context?) {

    companion object{
        private const val LOGIN_SHARED_FILE = "SAMPLE_LOGIN_FILE"
        private const val TOKEN = "TOKEN"
    }

    val userdata = context?.getSharedPreferences(LOGIN_SHARED_FILE, Context.MODE_PRIVATE)

    fun clearAll() {
        userdata?.edit()?.clear()?.apply()
    }

    fun setToken(data: String?) {
        val editor = userdata?.edit()
        editor?.putString(TOKEN,data)
        editor?.apply()

    }
    fun getToken(): String? {
        return userdata?.getString(TOKEN,null)
    }
}