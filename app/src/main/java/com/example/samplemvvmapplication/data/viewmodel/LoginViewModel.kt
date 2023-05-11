package com.example.samplemvvmapplication.data.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.network.JsonFunctionsApi
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.repository.LoginRepository
import com.example.samplemvvmapplication.utils.CommonFunctions
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val jsonFunctionsApi: JsonFunctionsApi,
    private val commonFunctions: CommonFunctions
): ViewModel() {

    val emailMuatabledata = MutableLiveData("eve.holt@reqres.in")
    val passMuatabledata = MutableLiveData("cityslicka")

    val login: LiveData<Response<JsonElement>>
        get() = loginRepository.login

    fun clickLogin(view : View){
        val ctx = view.context
        if (!commonFunctions.isValidEmail(emailMuatabledata.value)){
            Toast.makeText(ctx, ctx.resources.getString(R.string.email_hint),Toast.LENGTH_LONG).show()
        }else if (passMuatabledata.value.isNullOrEmpty()){
            Toast.makeText(ctx, ctx.resources.getString(R.string.password_hint),Toast.LENGTH_LONG).show()
        }else{
            viewModelScope.launch {
                loginRepository.getLoginResponse(jsonFunctionsApi.login(emailMuatabledata.value, passMuatabledata.value))
            }
        }
    }
}