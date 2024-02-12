package com.softeer.togeduck.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imgLoad")
fun loadImg(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}