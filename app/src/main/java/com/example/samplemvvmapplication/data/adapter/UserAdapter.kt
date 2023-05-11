package com.example.samplemvvmapplication.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemvvmapplication.R
import com.example.samplemvvmapplication.data.interfaces.ClickItem
import com.example.samplemvvmapplication.data.model.Data
import com.example.samplemvvmapplication.databinding.UserListInflatorBinding
import com.example.samplemvvmapplication.generated.callback.OnClickListener

class UserAdapter(
    val clickListener: ClickItem
) : PagingDataAdapter<Data, UserAdapter.ViewHolder>(ListDiffUtil()) {

    class ListDiffUtil : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(val binding : UserListInflatorBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val vw = UserListInflatorBinding.inflate(layoutInflater,parent,false)
        return  ViewHolder(vw)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            txtName.text = getItem(position)?.firstName + " " + getItem(position)?.lastName
            txtEmail.text = getItem(position)?.email

            Glide
                .with(root.context)
                .load(getItem(position)?.avatar)
                .placeholder(ContextCompat.getDrawable(root.context,R.drawable.no_image))
                .into(profileImage);
            root.setOnClickListener{
                clickListener.getData(getItem(position))
            }
        }
    }


}