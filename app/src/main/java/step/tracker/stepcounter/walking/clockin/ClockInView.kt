package step.tracker.stepcounter.walking.clockin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class ClockInView : View {

    val circlePaint: Paint = Paint()
    private val rect = Rect()


    constructor(context: Context) : super(context) {

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun getFontHeight(paint: Paint, text: String): Int {

        paint.getTextBounds(text, 0, text.length, rect)
        return rect.height()
    }

    fun init(context: Context) {

        circlePaint.isAntiAlias = true
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.textAlign = Paint.Align.CENTER
        circlePaint.textSize = ScreenUtil.sp2px(context, 12f)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var avgWidth: Int = width / 5

        val radius = ScreenUtil.dip2px(context, 20f).toFloat()




        for (i in 0..5) {

            val startX = ((avgWidth / 2) + avgWidth * i).toFloat()


            if(i<4){
                canvas?.drawLine(startX + radius, (height / 2).toFloat(), startX + avgWidth - radius, (height / 2).toFloat(), circlePaint)
            }

            canvas?.drawCircle(startX, (height / 2).toFloat(), radius, circlePaint)
            canvas?.drawText((i + 1).toString(), startX, (height / 2).toFloat() + getFontHeight(circlePaint, "0") / 2, circlePaint)
        }

    }
}