package step.tracker.stepcounter.walking.clockin

import android.app.Dialog
import android.content.Context
import android.view.View
import com.example.chenyi.clockin.R
import kotlinx.android.synthetic.main.dialog_clock_in.*

class ClockInDialog : Dialog {


    constructor(context: Context?) : this(context, R.style.Dialog_Alert_Fullscreen)

    constructor(context: Context?, themeResId: Int) : super(context, themeResId) {
        setContentView(R.layout.dialog_clock_in)
        clock_in_view.setClockInCompletedCount(0)
        clock_in_view.setOnClickClockInListener(object : OnClickClockInListener {
            override fun onClickClockIn(index: Int) {
                desc.showNext()
            }
        })

    }

    fun setDialogType(type: Int) {

        if (type == TODAY_CLOCK_IN) {
            today_clock_in_group.visibility = View.VISIBLE
            tomorrow_clock_in_group.visibility = View.GONE
        } else {
            today_clock_in_group.visibility = View.GONE
            tomorrow_clock_in_group.visibility = View.VISIBLE
        }
    }

    companion object {

        val TODAY_CLOCK_IN: Int = 1
        val TOMORROW_CLOCK_IN: Int = 2

    }
}