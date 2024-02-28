package com.softeer.togeduck.utils

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.softeer.togeduck.R
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.RecruitStatus

@BindingAdapter("imgLoad")
fun loadImg(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("recruitmentStatusTag")
fun loadRecruitmentStatusTag(view: TextView, status: RecruitStatus?) {
    view.text = status?.value

    val background: GradientDrawable = view.background as GradientDrawable
    val bgColor = when (status) {
        RecruitStatus.RECRUIT -> R.color.mint
        RecruitStatus.RECRUIT_COMPLETE -> R.color.navy300
        RecruitStatus.OPERATION_CONFIRM -> R.color.navy800
        RecruitStatus.OPERATION_COMPLETE -> R.color.gray400
        else -> R.color.main
    }
    background.setColor(ContextCompat.getColor(view.context, bgColor))
}

@BindingAdapter("s3ImageUrl")
fun setS3ImageUrl(view: ImageView, url: String?) {
    val convertedUrl = url?.convertS3Url() // 확장 함수 사용
    Log.d("Imgurl", convertedUrl.toString())
    Glide.with(view.context)
        .load(convertedUrl)
        .into(view)
}

@BindingAdapter("recruitStatusTextColor")
fun loadRecruitStatusTextColor(view: TextView, status: RecruitStatus) {
    val textColor = when (status) {
        RecruitStatus.RECRUIT -> R.color.main
        RecruitStatus.RECRUIT_COMPLETE -> R.color.navy300
        RecruitStatus.OPERATION_CONFIRM -> R.color.navy800
        RecruitStatus.OPERATION_COMPLETE -> R.color.gray400
    }
    view.setTextColor(ContextCompat.getColor(view.context, textColor))
}