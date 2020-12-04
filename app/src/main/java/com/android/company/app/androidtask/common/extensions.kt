package com.android.company.app.androidtask.common

import android.content.Context
import android.widget.ImageView
import com.android.company.app.androidtask.R
import com.bumptech.glide.Glide


fun String.setIcon(imageView: ImageView){

    Glide.with(imageView).load(this).placeholder(R.drawable.ic_launcher_background).into(imageView)
}