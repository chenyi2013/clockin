package step.tracker.stepcounter.walking.clockin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chenyi.clockin.R
import kotlinx.android.synthetic.main.dialog_clock_in.*

class ClockInDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in_detail)
        clock_in_view.setOnClickClockInListener(object : OnClickClockInListener {
            override fun onClickClockIn(index: Int) {
                desc.showNext()
            }
        })
    }
}
