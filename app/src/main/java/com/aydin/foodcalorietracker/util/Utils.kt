package com.aydin.foodcalorietracker.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadImage(url : String, circularProgressDrawable: CircularProgressDrawable){
    val option = RequestOptions().placeholder(circularProgressDrawable)
    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}

fun makeNewPlaceholder(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}