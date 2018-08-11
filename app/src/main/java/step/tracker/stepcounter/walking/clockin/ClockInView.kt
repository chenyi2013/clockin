package step.tracker.stepcounter.walking.clockin

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.chenyi.clockin.R

class ClockInView : View {


    private val circlePaint: Paint = Paint()
    private val animationCirclePaint: Paint = Paint()
    private val fillCirclePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val linePaint: Paint = Paint()

    private var bitmap: Bitmap? = null
    private var animation: ValueAnimator? = null

    private var selectRadius: Float = 0f
    private var rationDegree: Float = 0f
    private var scaleFactor: Float = 1f
    private var animationCircleRadiu: Float = 0f
    private var strokeWidth: Float = 5f

    private var clockInIndex: Int = 0
    private var clockInCompletedCount: Int = 0
    private var clockInTotalCount: Int = 5
    private var completedColor: Int = Color.RED

    private var isClockInAnimation: Boolean = false
    private var isCompletedTodayClockIn = false
    private var isRuningAnimation = false
    private val bufferRect = Rect()
    private val hintClickRect = RectF()
    private var radius: Float = 0f
    var imgMatrix: Matrix = Matrix()


    constructor(context: Context) : super(context) {

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun setClockInTotalCount(clockInTotalCount: Int) {
        this.clockInTotalCount = clockInTotalCount
        invalidate()
    }


    fun setClockInCompletedCount(clockInCompletedCount: Int) {
        this.clockInCompletedCount = clockInCompletedCount
        this.clockInIndex = clockInCompletedCount
        invalidate()
    }

    private fun getFontHeight(paint: Paint, text: String): Int {

        paint.getTextBounds(text, 0, text.length, bufferRect)
        return bufferRect.height()
    }

    private fun startAnimation() {
        animation = ObjectAnimator.ofFloat(1.2f, 1.4f)
        animation?.duration = 1000
        animation?.repeatMode = ValueAnimator.REVERSE
        animation?.repeatCount = ValueAnimator.INFINITE
        animation?.addUpdateListener {
            if (it.animatedValue is Float) {
                animationCircleRadiu = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                invalidate()
            }

        }
        animation?.start()
    }

    public fun setCompletedTodayClockIn(isCompletedTodayClockIn: Boolean) {
        this.isCompletedTodayClockIn = isCompletedTodayClockIn
        animation?.cancel()
        invalidate()
    }

    fun init(context: Context) {


        strokeWidth = ScreenUtil.dip2px(context, 2.0f).toFloat()



        circlePaint.isAntiAlias = true
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.textAlign = Paint.Align.CENTER
        circlePaint.strokeWidth = strokeWidth



        animationCirclePaint.isAntiAlias = true
        animationCirclePaint.color = Color.parseColor("#2ce3845e")
        animationCirclePaint.style = Paint.Style.FILL



        fillCirclePaint.isAntiAlias = true
        fillCirclePaint.color = Color.WHITE
        fillCirclePaint.style = Paint.Style.FILL



        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = ScreenUtil.sp2px(context, 17f)


        linePaint.isAntiAlias = true
        linePaint.strokeWidth = strokeWidth



        completedColor = Color.parseColor("#e78673")

        var icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_steps_per_day_1)
        if (icon != null && !icon.isRecycled) {
            bitmap = Bitmap.createScaledBitmap(icon, ScreenUtil.dip2px(context, 38f), ScreenUtil.dip2px(context, 38f), true)
        }

        selectRadius = ScreenUtil.dip2px(context, 20f).toFloat()
        radius = ScreenUtil.dip2px(context, 20f).toFloat()

        setOnClickListener {
        }

        startAnimation()

    }

    private fun startScaleUpAnimation(context: Context) {

        this.animation?.cancel()
        val animation: ValueAnimator = ObjectAnimator.ofFloat(0f, 1f)

        animation.duration = 1000
        animation.repeatCount = 0
        animation.addUpdateListener {

            if (it.animatedValue is Float) {

                val animatedValue: Float = it.animatedValue as Float

                animationCircleRadiu = animatedValue * radius
                selectRadius = animatedValue * radius

                if (selectRadius > radius) {
                    selectRadius = radius
                }

                isClockInAnimation = true
                rationDegree = 360 * animatedValue
                scaleFactor = animatedValue

                invalidate()
            }

        }

        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
        animation.start()


    }

    private fun startScaleDownAnimation(context: Context) {

        this.animation?.cancel()
        val animation: ValueAnimator = ObjectAnimator.ofFloat(1.2f, 1.6f, 0f)

        animation.duration = 2000
        animation.repeatCount = 0

        animation.addUpdateListener {

            if (it.animatedValue is Float) {

                val animatedValue = it.animatedValue as Float

                animationCircleRadiu = animatedValue * radius
                selectRadius = animatedValue * radius

                if (selectRadius > radius) {

                    selectRadius = radius

                }

                scaleFactor = animatedValue

                if (scaleFactor > 1.0f) {
                    scaleFactor = 1.0f
                }

                invalidate()
            }

        }
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                startScaleUpAnimation(context)
            }

        })
        animation.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (isCompletedTodayClockIn) {
            return super.onTouchEvent(event)
        }

        var avgWidth: Int = width / 5
        val startX = ((avgWidth / 2) + avgWidth * clockInIndex).toFloat()

        hintClickRect.set(startX - radius, 0.0f, startX + radius, height.toFloat())

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (hintClickRect.contains(event.x, event.y)) {

                    if (!isRuningAnimation) {
                        isRuningAnimation = true
                        startScaleDownAnimation(context)
                    }

                }
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                return true
            }

            else -> {
                return super.onTouchEvent(event)
            }

        }

        return super.onTouchEvent(event)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var avgWidth: Int = width / 5
        for (i in 0 until clockInTotalCount) {
            val startX = ((avgWidth / 2) + avgWidth * i).toFloat()
            setPaintColor(i)
            drawLine(i, canvas, startX, avgWidth)
            drawCircles(canvas, startX, i, avgWidth)
            drawText(i, canvas, startX)
            drawLastBitmapPoint(i, canvas, startX)
        }

    }

    private fun drawLastBitmapPoint(index: Int, canvas: Canvas?, startX: Float) {
        if (index == clockInTotalCount - 1) {

            var ringWidth: Int = height / 2 - ScreenUtil.dip2px(context, 38f) / 2

            if (!(isClockInAnimation || isCompletedTodayClockIn) && clockInIndex == index) {

                imgMatrix.setScale(scaleFactor, scaleFactor, (ScreenUtil.dip2px(context, 38f) / 2).toFloat(), (ScreenUtil.dip2px(context, 38f) / 2).toFloat())
                canvas?.save()
                canvas?.translate(startX - ScreenUtil.dip2px(context, 38f) / 2, ringWidth.toFloat())
                canvas?.drawBitmap(bitmap, imgMatrix, circlePaint)
                canvas?.restore()
            } else if (clockInIndex != index) {
                canvas?.drawBitmap(bitmap, startX - ScreenUtil.dip2px(context, 38f) / 2, ringWidth.toFloat(), circlePaint)
            }

        }
    }

    private fun drawText(index: Int, canvas: Canvas?, startX: Float) {
        if (index == clockInIndex) {
            canvas?.save()
            canvas?.rotate(rationDegree, startX, (height / 2).toFloat())
            canvas?.scale(scaleFactor, scaleFactor, startX, (height / 2).toFloat())
            if (isClockInAnimation || isCompletedTodayClockIn) {
                canvas?.drawText("√", startX, (height / 2).toFloat() + getFontHeight(textPaint, "0") / 2, textPaint)
            } else {
                canvas?.drawText((index + 1).toString(), startX, (height / 2).toFloat() + getFontHeight(textPaint, "0") / 2, textPaint)
            }
            canvas?.restore()
        } else {

            if (clockInCompletedCount >= (index + 1)) {
                canvas?.drawText("√", startX, (height / 2).toFloat() + getFontHeight(textPaint, "0") / 2, textPaint)
            } else {
                canvas?.drawText((index + 1).toString(), startX, (height / 2).toFloat() + getFontHeight(textPaint, "0") / 2, textPaint)
            }
        }
    }

    private fun drawLine(index: Int, canvas: Canvas?, startX: Float, avgWidth: Int) {
        if (index < clockInTotalCount - 1) {
            canvas?.drawLine(startX + radius, (height / 2).toFloat(), startX + avgWidth - radius, (height / 2).toFloat(), linePaint)
        }
    }

    private fun drawCircles(canvas: Canvas?, startX: Float, i: Int, avgWidth: Int) {
        canvas?.drawCircle(startX, (height / 2).toFloat(), radius + strokeWidth, fillCirclePaint)


        fillCirclePaint.color = Color.WHITE
        if (i == clockInIndex) {

            if (!isCompletedTodayClockIn) {
                canvas?.drawCircle(((avgWidth / 2) + avgWidth * clockInIndex).toFloat(), (height / 2).toFloat(), animationCircleRadiu, animationCirclePaint)
            }

            canvas?.drawCircle(startX, (height / 2).toFloat(), selectRadius, fillCirclePaint)

        } else {
            canvas?.drawCircle(startX, (height / 2).toFloat(), radius, fillCirclePaint)
        }
    }

    private fun setPaintColor(index: Int) {
        if (index <= clockInCompletedCount) {
            fillCirclePaint.color = completedColor
            circlePaint.color = completedColor
            textPaint.color = completedColor
            linePaint.color = completedColor

        } else {
            circlePaint.color = Color.parseColor("#e0e0e0")
            fillCirclePaint.color = Color.parseColor("#e0e0e0")
            textPaint.color = Color.parseColor("#e0e0e0")
            linePaint.color = Color.parseColor("#e0e0e0")
        }

        if (index == clockInIndex) {
            linePaint.color = Color.parseColor("#e0e0e0")
        }
    }
}