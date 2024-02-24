package com.softeer.togeduck.utils

import android.graphics.drawable.GradientDrawable
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