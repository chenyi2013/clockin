package step.tracker.stepcounter.walking.clockin

import android.app.Dialog
import android.content.Context
import com.example.chenyi.clockin.R

class ClockInDialog : Dialog {

    constructor(context: Context?) : this(context, R.style.Dialog_Alert_Fullscreen)

    constructor(context: Context?, themeResId: Int) : super(context, themeResId) {
        setContentView(R.layout.dialog_clock_in)
    }
}