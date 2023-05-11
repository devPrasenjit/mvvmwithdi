package com.example.samplemvvmapplication.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.adapter.PagingFooterAdapter
import com.example.samplemvvmapplication.data.adapter.UserAdapter
import com.example.samplemvvmapplication.data.interfaces.ClickItem
import com.example.samplemvvmapplication.data.localdata.Userdata
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.data.viewmodel.DashboardViewModel
import com.example.samplemvvmapplication.databinding.DashboardActivityBinding
import com.example.samplemvvmapplication.utils.CommonFunctions
import com.example.samplemvvmapplication.utils.Constants.ID
import com.example.samplemvvmapplication.utils.Constants.data_details
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class Dashboard : AppCompatActivity(), ClickItem {
    lateinit var binding: DashboardActivityBinding
    lateinit var viewModel : DashboardViewModel

    @Inject
    lateinit var commonFunctions: CommonFunctions

    @Inject
    lateinit var userdata: Userdata

    var loader : Dialog?= null
    private var userAdapter : UserAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.dashboard_activity)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        binding.lifecycleOwner = this
        init()
        observeUserList()
    }

    private fun init() {
        loader = commonFunctions.custom_loader(this)
        viewModel.getUserList()
    }

    private fun observeUserList() {
            viewModel.data_list.observe(this){
                userAdapter?.submitData(lifecycle, it)
            }

        userAdapter = UserAdapter(this)
        binding.rcvUserList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rcvUserList.adapter = userAdapter?.withLoadStateFooter(
            footer = PagingFooterAdapter {
                userAdapter?.retry()
            }
        )

        userAdapter?.addLoadStateListener { loadState ->
            when (loadState.refresh) {

                is LoadState.Loading ->
                    if (!loader?.isShowing!!) {
                        loader?.show()
                    }
                is LoadState.NotLoading ->
                    loader?.dismiss()
                is LoadState.Error -> {
                    loader?.dismiss()
                    val loadStateError = loadState.refresh as LoadState.Error
                    if (loadStateError.error is HttpException) {
                        Toast.makeText(binding.root.context,resources.getString(R.string.network_error_msg),Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(binding.root.context,resources.getString(R.string.server_error_msg),Toast.LENGTH_LONG).show()
                    }
                }
                else -> {
                    loader?.dismiss()
                }
            }
            println("item count adapter.." + userAdapter!!.itemCount)
            if (loadState.append.endOfPaginationReached && userAdapter?.itemCount == 0) {
                println("Error data adapter")
                println("item count adapter.." + userAdapter!!.itemCount)
            }
        }
    }

    override fun getData(data: Data?) {
        startActivity(Intent(this@Dashboard,Details::class.java).putExtra(data_details,data))
    }
}