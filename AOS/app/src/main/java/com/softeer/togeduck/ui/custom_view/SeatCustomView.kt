package com.softeer.togeduck.ui.custom_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softeer.togeduck.R
import com.softeer.togeduck.utils.fromDpToPx

class SeatCustomView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    companion object {
        private const val SEAT_SIZE = 50
        private const val SEAT_MARGIN_SIZE = 10
    }

    private var mode: SeatViewMode

    private var seatsCntPerRow = 4
    private var rearSeatsCnt = 5
    private var totalRows = 10

    private var seatColWithAisleLeft: Int
    private var aisleSize: Int
    private var viewWidthSize: Int
    private var viewHeightSize: Int

    private var selectedSeatNum: Int
    private var selectedSeat: TextView?

    private var _selectSeat = MutableLiveData<Boolean>(false)
    val selectSeat: LiveData<Boolean> get() = _selectSeat

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SeatCustomView, 0, 0).apply {
            try {
                mode = SeatViewMode.values()[this.getInt(R.styleable.SeatCustomView_mode, 0)]
            } finally {
                recycle()
            }
        }

        seatColWithAisleLeft = calcSeatColWithAisleLeft()
        aisleSize = calcAisleSize()
        viewWidthSize =
            ((SEAT_SIZE + SEAT_MARGIN_SIZE) * seatsCntPerRow - SEAT_MARGIN_SIZE + aisleSize).fromDpToPx()
        viewHeightSize = ((SEAT_SIZE + SEAT_MARGIN_SIZE) * totalRows + SEAT_SIZE).fromDpToPx()

        selectedSeatNum = -1
        selectedSeat = null

        addAllSeatsToView()
    }

    private fun calcSeatColWithAisleLeft() = when (seatsCntPerRow) {
        2 -> 1
        3, 4 -> 2
        else -> -1
    }

    private fun calcAisleSize(): Int {
        val seatDiffCntInRearAndOtherRow = rearSeatsCnt - seatsCntPerRow

        return if (seatDiffCntInRearAndOtherRow == 0) {
            SEAT_SIZE
        } else {
            seatDiffCntInRearAndOtherRow * (SEAT_SIZE + SEAT_MARGIN_SIZE)
        }
    }


    private fun addAllSeatsToView() {
        for (row in 0..<totalRows) {
            for (col in 0..<seatsCntPerRow) {
                val seatNumber = row * seatsCntPerRow + col + 1
                val item = if (col < seatColWithAisleLeft) {
                    createSeat(col, row, 0)
                } else {
                    createSeat(
                        col, row, aisleSize
                    )
                }

                addSeatToView(item, seatNumber)
            }
        }
        for (col in 0..<rearSeatsCnt) { // 마지막 줄 좌석 추가
            val seatNumber = totalRows * seatsCntPerRow + col + 1
            val item = createSeat(col, totalRows, 0)

            addSeatToView(item, seatNumber)
        }
    }

    private fun createSeat(col: Int, row: Int, addedLeftMargin: Int): TextView {
        val item = TextView(context)
        val leftPos = col * (SEAT_SIZE + SEAT_MARGIN_SIZE) + addedLeftMargin
        val topPos = row * (SEAT_SIZE + SEAT_MARGIN_SIZE)

        val lp = LayoutParams(viewWidthSize, viewHeightSize)
        lp.run {
            lp.width = SEAT_SIZE.fromDpToPx()
            lp.height = SEAT_SIZE.fromDpToPx()
            lp.topMargin = topPos.fromDpToPx()
            lp.leftMargin = leftPos.fromDpToPx()
        }

        return item.apply {
            layoutParams = lp
            gravity = Gravity.CENTER
        }
    }

    private fun addSeatToView(item: TextView, seatNumber: Int) {
        setSeatItemProperty(item, seatNumber)

        if (mode == SeatViewMode.SELECTABLE) {
            item.setOnClickListener {
                val clickedSeatNum = item.text.toString().toInt()
                if (selectedSeatNum == clickedSeatNum) {
                    deselectSeat(item, seatNumber)
                } else {
                    selectSeat(item, seatNumber)
                }
            }
        }
        addView(item)
    }

    private fun setSeatItemProperty(item: TextView, seatNumber: Int) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.seat_item)
            text = seatNumber.toString()
            setTextColor(Color.BLACK)
        }
    }


    private fun setSelectedSeatItemProperty(item: TextView) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.selected_seat_item)
            setTextColor(Color.WHITE)
        }
    }

    private fun setSoldOutSeatItemProperty(item: TextView, seatNumber: Int) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.sold_out_seat_item)
            text = ""
        }
    }

    private fun deselectSeat(item: TextView, seatNumber: Int) {
        selectedSeatNum = -1
        selectedSeat = null
        _selectSeat.value = false

        setSeatItemProperty(item, seatNumber)
    }

    private fun selectSeat(item: TextView, seatNumber: Int) {
        if (selectedSeat != null) {
            setSeatItemProperty(selectedSeat!!, selectedSeat!!.text.toString().toInt())
        }

        selectedSeatNum = seatNumber
        selectedSeat = item
        _selectSeat.value = true

        setSelectedSeatItemProperty(item)
    }

    fun updateSeatChart(seatsCntPerRow: Int, totalRows: Int, rearSeatsCnt: Int) {
        this.seatsCntPerRow = seatsCntPerRow
        this.totalRows = totalRows
        this.rearSeatsCnt = rearSeatsCnt
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(viewWidthSize, viewHeightSize)
    }

}

enum class SeatViewMode {
    SELECTABLE, READ_ONLY
}