package step.tracker.stepcounter.walking.clockin

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.chenyi.clockin.R

class ClockInView : View {

    val circlePaint: Paint = Paint()
    val anCirPaint: Paint = Paint()
    val fillCirclePaint: Paint = Paint()
    var bitmap: Bitmap? = null
    var animation: ValueAnimator? = null
    var selectRadius: Float = 0f

    private val rect = Rect()
    var aniCir: Float = 0f


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

    private fun startAnimation() {
        animation = ObjectAnimator.ofFloat(1.2f, 1.4f)
        animation?.duration = 1000
        animation?.repeatMode = ValueAnimator.REVERSE
        animation?.repeatCount = ValueAnimator.INFINITE
        animation?.addUpdateListener {
            if (it.animatedValue is Float) {
                aniCir = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                invalidate()
            }

        }
        animation?.start()
    }

    fun init(context: Context) {

        circlePaint.isAntiAlias = true
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.textAlign = Paint.Align.CENTER
        circlePaint.textSize = ScreenUtil.sp2px(context, 12f)



        anCirPaint.isAntiAlias = true
        anCirPaint.color = Color.RED
        anCirPaint.style = Paint.Style.FILL



        fillCirclePaint.isAntiAlias = true
        fillCirclePaint.color = Color.WHITE
        fillCirclePaint.style = Paint.Style.FILL

        var icon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_steps_per_day_1)
        if (icon != null && !icon.isRecycled) {
            bitmap = Bitmap.createScaledBitmap(icon, ScreenUtil.dip2px(context, 38f), ScreenUtil.dip2px(context, 38f), true)
        }

        selectRadius = ScreenUtil.dip2px(context, 20f).toFloat()

        setOnClickListener {
            startScaleDownAnimation(context)
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
                aniCir = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                selectRadius = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                if (selectRadius > ScreenUtil.dip2px(context, 20f).toFloat()) {
                    selectRadius = ScreenUtil.dip2px(context, 20f).toFloat()
                }
                invalidate()
            }

        }
        animation.start()


    }

    private fun startScaleDownAnimation(context: Context) {
        this.animation?.cancel()
        val animation: ValueAnimator = ObjectAnimator.ofFloat(1.2f, 1.6f, 0f)
        animation.duration = 2000
        animation.repeatCount = 0
        animation.addUpdateListener {
            if (it.animatedValue is Float) {
                aniCir = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                selectRadius = it.animatedValue as Float * ScreenUtil.dip2px(context, 20f).toFloat()
                if (selectRadius > ScreenUtil.dip2px(context, 20f).toFloat()) {
                    selectRadius = ScreenUtil.dip2px(context, 20f).toFloat()
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

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//
//        when (event?.action) {
//
//            MotionEvent.ACTION_DOWN -> {
//                return true
//
//            }
//            MotionEvent.ACTION_UP,
//            MotionEvent.ACTION_CANCEL -> {
//                return true
//            }
//
//            else -> {
//                return super.onTouchEvent(event)
//            }
//
//        }
//        return super.onTouchEvent(event)
//
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var avgWidth: Int = width / 5

        val radius = ScreenUtil.dip2px(context, 20f).toFloat()




        canvas?.drawCircle(((avgWidth / 2) + avgWidth * 0).toFloat(), (height / 2).toFloat(), aniCir, anCirPaint)

        for (i in 0..5) {

            val startX = ((avgWidth / 2) + avgWidth * i).toFloat()


            if (i < 4) {
                canvas?.drawLine(startX + radius, (height / 2).toFloat(), startX + avgWidth - radius, (height / 2).toFloat(), circlePaint)
            }


            if (i == 0) {
                fillCirclePaint.color = Color.BLUE
                canvas?.drawCircle(startX, (height / 2).toFloat(), radius + 5, fillCirclePaint)

            } else {
                fillCirclePaint.color = Color.BLACK
                canvas?.drawCircle(startX, (height / 2).toFloat(), radius + 5, fillCirclePaint)

            }

            fillCirclePaint.color = Color.WHITE
            if (i == 0) {
                canvas?.drawCircle(startX, (height / 2).toFloat(), selectRadius, fillCirclePaint)

            } else {
                canvas?.drawCircle(startX, (height / 2).toFloat(), radius, fillCirclePaint)
            }





            canvas?.drawText((i + 1).toString(), startX, (height / 2).toFloat() + getFontHeight(circlePaint, "0") / 2, circlePaint)

            if (i == 4) {
                var a: Int = height / 2 - ScreenUtil.dip2px(context, 38f) / 2;
                canvas?.drawBitmap(bitmap, startX - ScreenUtil.dip2px(context, 38f) / 2, a.toFloat(), circlePaint)
            }

        }

    }
}