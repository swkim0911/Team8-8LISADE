package com.softeer.togeduck.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//////////추후 체크 필요!!!!//////////
class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = spacing
        outRect.left = spacing
        outRect.top = spacing
        outRect.bottom = spacing
    }
}

