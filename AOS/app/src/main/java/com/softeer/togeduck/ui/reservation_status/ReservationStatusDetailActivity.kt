package com.softeer.togeduck.ui.reservation_status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityReservationStatusDetailBinding

class ReservationStatusDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationStatusDetailBinding
    private val reserveDetailViewModel: ReservationStatusDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_status_detail)

        init()
    }

    private fun init() {
        binding.vm = reserveDetailViewModel
    }
}