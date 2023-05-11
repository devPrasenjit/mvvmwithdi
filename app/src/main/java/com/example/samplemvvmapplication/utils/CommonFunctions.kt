package com.example.samplemvvmapplication.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.util.Patterns
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.samplemvvmapplication.R
import javax.inject.Inject


class CommonFunctions @Inject constructor(){
//    fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && target?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } == true
    }

    fun custom_loader(ctx: Context?): Dialog? {
        var dialog: Dialog? = null
        ctx?.let {
            dialog = Dialog(it)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.loader_xml)

//            dialog?.show()
        }
        return dialog

    }
}