package com.softeer.togeduck.ui.home.seat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softeer.togeduck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)
    }
}