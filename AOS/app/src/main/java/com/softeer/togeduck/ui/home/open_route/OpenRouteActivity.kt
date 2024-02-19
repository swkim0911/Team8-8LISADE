package com.softeer.togeduck.ui.home.open_route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softeer.togeduck.databinding.ActivityOpenRouteBinding

class OpenRouteActivity : AppCompatActivity() {
    private var _binding: ActivityOpenRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.leftLayoutView.setOnClickListener {
            RegionListDialog().show(supportFragmentManager, "ListDialogFragment")
        }
    }
}


