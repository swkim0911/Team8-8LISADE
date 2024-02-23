package com.softeer.togeduck.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imgLoad")
fun loadImg(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("s3ImageUrl")
fun setS3ImageUrl(view:ImageView, url:String){
    val convertedUrl = url.convertS3Url() // 확장 함수 사용
    Log.d("TESTLOG4444",convertedUrl)
    Glide.with(view.context)
        // 왜 이건 이미지 뜨고
//        .load("https://t1.daumcdn.net/cfile/tistory/2436014554FC557F2E")
        // 이건 안뜨지?
        .load(convertedUrl)
        .into(view)
}