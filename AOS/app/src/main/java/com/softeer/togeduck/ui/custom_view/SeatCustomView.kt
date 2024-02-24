package com.softeer.togeduck.ui.custom_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.softeer.togeduck.R
import com.softeer.togeduck.utils.ItemClickWithSeat
import com.softeer.togeduck.utils.fromDpToPx

class SeatCustomView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    companion object {
        private const val SEAT_SIZE = 50
        private const val SEAT_MARGIN_SIZE = 10
    }

    private var mode: SeatViewMode
    private var seatLayout: SeatLayoutModel
    private var seatDiffCntInBackAndOtherRow: Int
    private var seatLayoutSize: SeatLayoutSizeModel

    private var seatItems: MutableList<TextView>
    private var selectedSeatNum: Int
    private var selectedSeat: TextView?

    var itemClick: ItemClickWithSeat? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SeatCustomView, 0, 0).run {
            try {
                mode = SeatViewMode.values()[getInt(R.styleable.SeatCustomView_mode, 0)]
                seatLayout = SeatLayoutModel(
                    seatsCntPerRow = getInt(R.styleable.SeatCustomView_seatsCntPerRow, 0),
                    backSeatsCnt = getInt(R.styleable.SeatCustomView_backSeatsCnt, 0),
                    totalRows = getInt(R.styleable.SeatCustomView_totalRows, 0),
                    mySeatNum = getInt(R.styleable.SeatCustomView_mySeatNum, 0),
                    soldOutSeatNums = stringToList(
                        getString(R.styleable.SeatCustomView_soldOutSeatNums) ?: ""
                    )
                )
            } finally {
                recycle()
            }
        }
        seatDiffCntInBackAndOtherRow = seatLayout.backSeatsCnt - seatLayout.seatsCntPerRow
        seatLayoutSize = SeatLayoutSizeModel(
            seatColWithAisleLeft = calcSeatColWithAisleLeft(),
            aisleSize = calcAisleSize(),
            viewWidthSize = ((SEAT_SIZE + SEAT_MARGIN_SIZE) * seatLayout.seatsCntPerRow - SEAT_MARGIN_SIZE + calcAisleSize()).fromDpToPx(),
            viewHeightSize = ((SEAT_SIZE + SEAT_MARGIN_SIZE) * seatLayout.totalRows + SEAT_SIZE).fromDpToPx()
        )

        selectedSeatNum = -1
        selectedSeat = null
        seatItems = mutableListOf()

        makeAllSeatTextView()
        loadSeatsToView()
    }

    private fun stringToList(input: String): List<Int> {
        val regex = Regex("\\d+")
        val matches = regex.findAll(input)
        return matches.map { it.value.toInt() }.toList()
    }

    private fun calcSeatColWithAisleLeft() = when (seatLayout.seatsCntPerRow) {
        2 -> 1
        3, 4 -> 2
        else -> -1
    }

    private fun calcAisleSize(): Int {
        return if (seatDiffCntInBackAndOtherRow == 0) {
            SEAT_SIZE
        } else {
            seatDiffCntInBackAndOtherRow * (SEAT_SIZE + SEAT_MARGIN_SIZE)
        }
    }

    private fun makeAllSeatTextView() {
        for (row in 0..<seatLayout.totalRows) {
            for (col in 0..<seatLayout.seatsCntPerRow) {
                val seatNumber = row * seatLayout.seatsCntPerRow + col + 1
                val item = if (col < seatLayoutSize.seatColWithAisleLeft) {
                    createSeat(col, row, 0, seatNumber)
                } else {
                    createSeat(col, row, seatLayoutSize.aisleSize, seatNumber)
                }
                seatItems.add(item)
            }
        }
        for (col in 0..<seatLayout.backSeatsCnt) { // 마지막 줄 좌석 추가
            val seatNumber = seatLayout.totalRows * seatLayout.seatsCntPerRow + col + 1
            val item =
                if (seatDiffCntInBackAndOtherRow == 0 && col >= seatLayoutSize.seatColWithAisleLeft) {
                    createSeat(col, seatLayout.totalRows, seatLayoutSize.aisleSize, seatNumber)
                } else {
                    createSeat(col, seatLayout.totalRows, 0, seatNumber)
                }
            seatItems.add(item)
        }
    }

    private fun loadSeatsToView() {
        var seatNum = 1
        for (seatTextView in seatItems) {
            addSeatToView(seatTextView, seatNum++)
        }
        if (mode == SeatViewMode.SELECTABLE) {
            checkSoldOutSeat()
        } else {
            checkMySeat()
        }
    }

    private fun createSeat(col: Int, row: Int, addedLeftMargin: Int, seatNumber: Int): TextView {
        val item = TextView(context)
        val leftPos = col * (SEAT_SIZE + SEAT_MARGIN_SIZE) + addedLeftMargin
        val topPos = row * (SEAT_SIZE + SEAT_MARGIN_SIZE)

        val lp = LayoutParams(seatLayoutSize.viewWidthSize, seatLayoutSize.viewHeightSize)
        lp.run {
            lp.width = SEAT_SIZE.fromDpToPx()
            lp.height = SEAT_SIZE.fromDpToPx()
            lp.topMargin = topPos.fromDpToPx()
            lp.leftMargin = leftPos.fromDpToPx()
        }

        if (mode == SeatViewMode.SELECTABLE) {
            item.setOnClickListener {
                val clickedSeatNum = item.text.toString().toInt()
                if (selectedSeatNum == clickedSeatNum) {
                    deselectSeat(item, seatNumber)
                    itemClick?.onClick(false)
                } else {
                    selectSeat(item, seatNumber)
                    itemClick?.onClick(true)
                }
            }
        }

        return item.apply {
            layoutParams = lp
            gravity = Gravity.CENTER
        }
    }

    private fun addSeatToView(item: TextView, seatNumber: Int) {
        setSeatItemProperty(item, seatNumber)
        addView(item)
    }

    private fun checkSoldOutSeat() {
        for (i in seatLayout.soldOutSeatNums) {
            setSoldOutSeatItemProperty(seatItems[i - 1])
        }
    }

    private fun checkMySeat() {
        setSelectedSeatItemProperty(seatItems[seatLayout.mySeatNum - 1])
    }

    private fun setSeatItemProperty(item: TextView, seatNumber: Int) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.seat_item)
            text = seatNumber.toString()
            setTextColor(Color.BLACK)
            isEnabled = true
        }
    }


    private fun setSelectedSeatItemProperty(item: TextView) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.selected_seat_item)
            setTextColor(Color.WHITE)
        }
    }

    private fun setSoldOutSeatItemProperty(item: TextView) {
        item.run {
            background = ContextCompat.getDrawable(context, R.drawable.sold_out_seat_item)
            text = ""
            isEnabled = false
        }
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

        setMeasuredDimension(seatLayoutSize.viewWidthSize, seatLayoutSize.viewHeightSize)
    }

}

enum class SeatViewMode {
    SELECTABLE, READ_ONLY
}

data class SeatLayoutModel(
    val seatsCntPerRow: Int,
    val backSeatsCnt: Int,
    val totalRows: Int,
    val mySeatNum: Int,
    val soldOutSeatNums: List<Int>,
)

data class SeatLayoutSizeModel(
    val seatColWithAisleLeft: Int,
    val aisleSize: Int,
    val viewWidthSize: Int,
    val viewHeightSize: Int,
)