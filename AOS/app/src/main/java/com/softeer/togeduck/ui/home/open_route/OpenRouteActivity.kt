package com.softeer.togeduck.ui.home.open_route

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.softeer.togeduck.R

class OpenRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_route)
        // 예를 들어, 버튼 클릭 시 다이얼로그를 표시합니다.
        val button = findViewById<View>(R.id.leftLayoutView)
        button.setOnClickListener {
            RegionListDialog().show(supportFragmentManager, "ListDialogFragment")
        }
    }
}


