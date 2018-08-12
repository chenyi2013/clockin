package step.tracker.stepcounter.walking.clockin


import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Path.Direction
import android.graphics.RectF
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View


class RoundImageView : AppCompatImageView {

    private val path = Path()
    private val rectF = RectF()
    private val radius = FloatArray(8)


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {

        val radiu: Float = ScreenUtil.dip2px(context, 8f).toFloat()

        radius[0] = radiu
        radius[1] = radiu
        radius[2] = radiu
        radius[3] = radiu

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        path.reset()
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rectF, radius, Direction.CCW)
        canvas.clipPath(path)
        super.onDraw(canvas)


    }
}
