package com.softeer.togeduck.utils

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.softeer.togeduck.R
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatus

@BindingAdapter("imgLoad")
fun loadImg(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("recruitmentStatusTag")
fun loadRecruitmentStatusTag(view: TextView, status: ReserveStatus?) {
    view.text = status?.value

    val background: GradientDrawable = view.background as GradientDrawable
    val bgColor = when (status) {
        ReserveStatus.RECRUIT -> R.color.mint
        ReserveStatus.RECRUIT_COMPLETE -> R.color.navy300
        ReserveStatus.OPERATION_CONFIRM -> R.color.navy800
        ReserveStatus.OPERATION_COMPLETE -> R.color.gray400
        else -> R.color.main
    }
    background.setColor(ContextCompat.getColor(view.context, bgColor))
}

@BindingAdapter("s3ImageUrl")
fun setS3ImageUrl(view:ImageView, url:String){
    val convertedUrl = url.convertS3Url() // 확장 함수 사용
    Log.d("TESTLOG66666",convertedUrl)
    Glide.with(view.context)
        // 왜 이건 이미지 뜨고
//        .load("https://t1.daumcdn.net/cfile/tistory/2436014554FC557F2E")
        // 이건 안뜨지?
        .load(convertedUrl)
        .into(view)
}