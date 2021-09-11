package com.ayhanunal.movies.util

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ayhanunal.movies.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadImage(url: String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.no_image_place_holder)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        setColorFilter(context.resources.getColor(R.color.app_main_color), PorterDuff.Mode.SRC_IN)
        strokeWidth = 10f
        centerRadius = 35f
        start()
    }
}