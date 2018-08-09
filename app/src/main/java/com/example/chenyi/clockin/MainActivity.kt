package com.example.chenyi.clockin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import step.tracker.stepcounter.walking.clockin.ClockInDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        click_me.setOnClickListener {

            ClockInDialog(this).show()

//            var intent:Intent  = Intent(this@MainActivity,ClockInActivity::class.java)
//            startActivity(intent)
        }
    }
}
