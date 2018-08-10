package com.example.chenyi.clockin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import step.tracker.stepcounter.walking.clockin.ClockInCompleteActivity
import step.tracker.stepcounter.walking.clockin.ClockInDetailActivity
import step.tracker.stepcounter.walking.clockin.ClockInDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showDialog.setOnClickListener {
            val dialog =  ClockInDialog(this)
            dialog.setDialogType(ClockInDialog.TODAY_CLOCK_IN)
            dialog.show()
        }

        showDialogTwo.setOnClickListener {
            val dialog =  ClockInDialog(this)
            dialog.setDialogType(ClockInDialog.TOMORROW_CLOCK_IN)
            dialog.show()
        }

        showClockInDetail.setOnClickListener {
            startActivity(Intent(this, ClockInDetailActivity::class.java))
        }

        showClockInComplete.setOnClickListener{
            startActivity(Intent(this,ClockInCompleteActivity::class.java))
        }
    }
}
