package com.example.samplemvvmapplication.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.localdata.Userdata
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.viewmodel.LoginViewModel
import com.example.samplemvvmapplication.databinding.LoginActivityBinding
import com.example.samplemvvmapplication.utils.CommonFunctions
import com.example.samplemvvmapplication.utils.Constants.token
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class Login : AppCompatActivity() {

    lateinit var loginBinding: LoginActivityBinding
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var commonFunctions: CommonFunctions

    @Inject
    lateinit var userdata: Userdata

    var loader : Dialog ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.login_activity)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginBinding.viewmodel = viewModel
        loginBinding.lifecycleOwner = this
        init()

        observeLoginData()
    }

    private fun init() {
        loader = commonFunctions.custom_loader(this)
    }

    private fun observeLoginData() {
        viewModel.login.observe(this){
            when(it){
                is Response.Loading->{
                    loader?.show()
                }

                is Response.Error -> {
                    loader?.dismiss()
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                }
                is Response.Success -> {
                    loader?.dismiss()
                    val jobj = JSONObject(it.data.toString())
                    userdata.setToken(jobj.getString(token))
                    startActivity(Intent(this@Login, Dashboard::class.java))
                    finish()
//                    Toast.makeText(this, it.data.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}