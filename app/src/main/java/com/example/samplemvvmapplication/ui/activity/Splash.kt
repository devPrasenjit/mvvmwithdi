package com.example.samplemvvmapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.localdata.Userdata
import com.example.samplemvvmapplication.data.viewmodel.DashboardViewModel
import com.example.samplemvvmapplication.databinding.SplashActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class Splash : AppCompatActivity(){
    lateinit var binding: SplashActivityBinding

    @Inject
    lateinit var userdata: Userdata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        binding.lifecycleOwner = this

        init()
    }

    private fun init() {
        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main){
                if (userdata.getToken().isNullOrEmpty())
                    startActivity(Intent(this@Splash, Login::class.java))
                else startActivity(Intent(this@Splash, Dashboard::class.java))

                finish()
            }}
    }
}