package com.softeer.togeduck.ui.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softeer.togeduck.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }

}
