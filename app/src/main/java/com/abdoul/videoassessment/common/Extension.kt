package com.abdoul.videoassessment.common

import android.widget.ImageView
import com.abdoul.videoassessment.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_image)
        .error(R.drawable.ic_broken_image)
        .into(this)
}