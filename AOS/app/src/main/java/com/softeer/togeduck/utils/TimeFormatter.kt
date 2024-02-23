package com.softeer.togeduck.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.Locale

object TimeFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun yyyyMMdd(time: String) : String {
        val yyyyMMdd = DateTimeFormatter.ofPattern("yyyy.MM.dd일", Locale.KOREA)

        return toLocalDateTime(time).format(yyyyMMdd)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun MMdd(time: String) : String {
        val MMdd = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREA)

        return toLocalDateTime(time).format(MMdd)
    }

    fun yesterday() : String {
        return "어제"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ahhmm(time: String) : String {
        val ahhmm = DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA)

        return toLocalDateTime(time).format(ahhmm)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDateTime(time: String) : LocalDateTime {
        val timeParser = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .toFormatter()

        return LocalDateTime.parse(time, timeParser)
    }
}