package com.softeer.togeduck.ui.home.seat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.softeer.togeduck.R
import com.softeer.togeduck.utils.fromDpToPx

class SeatSelectView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private val seatSize = 50
    private val seatMargin = 10
    private var seatsPerRow = 2
    private var totalRows = 10
    private var lastSeatsCnt = 5
    private val aisleSize = calcAisleSize()
    private var viewWidthSize =
        ((seatSize + seatMargin) * seatsPerRow - seatMargin + aisleSize).fromDpToPx()
    private var viewHeightSize = ((seatSize + seatMargin) * totalRows + seatSize).fromDpToPx()

    private var selectedSeatNum = -1
    private var selectedSeat: TextView? = null

    init {
        val aisleSeatCol = calcAisleSeatCol()
        for (row in 0..totalRows) {
            for (col in 0..<seatsPerRow) {
                val seatNumber = row * seatsPerRow + col + 1
                val item = if (col < aisleSeatCol) createSeat(col, row, 0)
                else
                    createSeat(
                        col,
                        row,
                        aisleSize
                    )

                addSeatToView(item, seatNumber)
            }
        }
        for (col in 0..<lastSeatsCnt) { // 마지막 줄 좌석 추가
            val seatNumber = totalRows * seatsPerRow + col + 1
            val item = createSeat(col, totalRows, 0)

            addSeatToView(item, seatNumber)
        }
    }

    private fun addSeatToView(item: TextView, seatNumber: Int) {
        setSeatItemProperty(item, seatNumber)

        item.setOnClickListener {
            val clickedSeatNum = item.text.toString().toInt()
            if (selectedSeatNum == clickedSeatNum) {
                deselectSeat(item, seatNumber)
            } else {
                selectSeat(item, seatNumber)
            }

        }
        addView(item)
    }

    private fun calcAisleSize(): Int {
        val numOfSeatsAddedToLast = lastSeatsCnt - seatsPerRow

        return numOfSeatsAddedToLast * (seatSize + seatMargin)
    }

    private fun calcAisleSeatCol() = when (seatsPerRow) {
        2 -> 1
        3, 4 -> 2
        else -> 0
    }

    private fun createSeat(col: Int, row: Int, addedLeftMargin: Int): TextView {
        val item = TextView(context)
        val leftPos = col * (seatSize + seatMargin) + addedLeftMargin
        val topPos = row * (seatSize + seatMargin)

        val lp = LayoutParams(viewWidthSize, viewHeightSize)
        lp.run {
            lp.width = seatSize.fromDpToPx()
            lp.height = seatSize.fromDpToPx()
            lp.topMargin = topPos.fromDpToPx()
            lp.leftMargin = leftPos.fromDpToPx()
        }

        return item.apply {
            layoutParams = lp
            gravity = Gravity.CENTER
        }
    }

    private fun setSeatItemProperty(item: TextView, seatNumber: Int) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.seat_item)
            text = seatNumber.toString()
            setTextColor(Color.BLACK)
        }
    }

    private fun setSelectedSeatItemProperty(item: TextView) {
        item.background =
            ContextCompat.getDrawable(context, R.drawable.selected_seat_item)
        item.setTextColor(Color.WHITE)
    }

    private fun setSoldOutSeatItemProperty(item: TextView, seatNumber: Int) {
        item.background =
            ContextCompat.getDrawable(context, R.drawable.sold_out_seat_item)
        item.text = ""
    }

    private fun deselectSeat(item: TextView, seatNumber: Int) {
        selectedSeatNum = -1
        selectedSeat = null

        setSeatItemProperty(item, seatNumber)
    }

    private fun selectSeat(item: TextView, seatNumber: Int) {
        if (selectedSeat != null) {
            setSeatItemProperty(selectedSeat!!, selectedSeat!!.text.toString().toInt())
        }

        selectedSeatNum = seatNumber
        selectedSeat = item

        setSelectedSeatItemProperty(item)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(viewWidthSize, viewHeightSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }

    fun updateSeatChart(seatsPerRow: Int, totalRows: Int, lastSeatsCnt: Int) {
        this.seatsPerRow = seatsPerRow
        this.totalRows = totalRows
        this.lastSeatsCnt = lastSeatsCnt

        invalidate()
    }
}