package com.softeer.togeduck.utils

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
fun loadRecruitmentStatusTag(view: TextView, status: ReserveStatus) {
    view.text = status.value

    view.setBackgroundColor(
        when (status) {
            ReserveStatus.RECRUIT -> {
                ContextCompat.getColor(view.context, R.color.mint)
            }

            ReserveStatus.RECRUIT_COMPLETE -> {
                ContextCompat.getColor(view.context, R.color.navy300)
            }

            ReserveStatus.OPERATION_CONFIRM -> {
                ContextCompat.getColor(view.context, R.color.navy800)
            }

            ReserveStatus.OPERATION_COMPLETE -> {
                ContextCompat.getColor(view.context, R.color.gray400)
            }
        }
    )
}