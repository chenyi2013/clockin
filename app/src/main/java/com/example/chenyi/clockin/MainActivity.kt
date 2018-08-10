package com.example.chenyi.clockin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import step.tracker.stepcounter.walking.clockin.ClockInDetailActivity
import step.tracker.stepcounter.walking.clockin.ClockInDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showDialog.setOnClickListener {
            ClockInDialog(this).show()
        }

        showDialogTwo.setOnClickListener {
            ClockInDialog(this).show()
        }

        showClockInDetail.setOnClickListener {
            startActivity(Intent(this, ClockInDetailActivity::class.java))
        }
    }
}
