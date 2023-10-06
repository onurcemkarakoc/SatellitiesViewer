package com.onurcemkarakoc.core.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.onurcemkarakoc.core.common.databinding.DialogLoadingBinding


class LoadingDialog(context: Context) : Dialog(context) {

    private var binding: DialogLoadingBinding =
        DialogLoadingBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        if (context is Activity) {
            setOwnerActivity(context)
        }

        window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}