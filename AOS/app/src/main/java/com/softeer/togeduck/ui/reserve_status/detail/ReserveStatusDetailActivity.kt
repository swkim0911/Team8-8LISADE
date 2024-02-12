package com.softeer.togeduck.ui.reserve_status.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.ActivityReserveStatusDetailBinding

class ReserveStatusDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReserveStatusDetailBinding
    private val reserveDetailViewModel: ReserveStatusDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserve_status_detail)

        init()
    }

    private fun init() {
        binding.vm = reserveDetailViewModel
    }
}