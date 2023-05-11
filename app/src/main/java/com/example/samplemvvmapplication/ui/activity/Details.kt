package com.example.samplemvvmapplication.ui.activity

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.network.Response
import com.example.samplemvvmapplication.data.viewmodel.DetailsViewModel
import com.example.samplemvvmapplication.databinding.UserDetailsActivityBinding
import com.example.samplemvvmapplication.utils.CommonFunctions
import com.example.samplemvvmapplication.utils.Constants.ID
import com.example.samplemvvmapplication.utils.Constants.data_details
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Details : AppCompatActivity() {
    lateinit var binding: UserDetailsActivityBinding
    lateinit var viewModel : DetailsViewModel

    @Inject
    lateinit var commonFunctions: CommonFunctions

    var loader : Dialog?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.user_details_activity)

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        loader = commonFunctions.custom_loader(this)
        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(data_details,Data::class.java)
        } else {
            intent.getParcelableExtra(data_details)
        }

        with(binding){
            txtUserName.text = resources.getString(R.string.name, data?.firstName, data?.lastName)
            txtUserEmail.text = resources.getString(R.string.email, data?.email)

            Glide
                .with(root.context)
                .load(data?.avatar)
                .placeholder(ContextCompat.getDrawable(root.context,R.drawable.no_image))
                .into(profileImage);
        }
    }

}