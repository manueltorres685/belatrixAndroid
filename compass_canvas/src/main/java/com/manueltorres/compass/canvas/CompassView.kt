package com.manueltorres.compass.canvas


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Manuel Torres on 2019-09-03.
 */
class CompassView : View {

    private var direction: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        // TODO Auto-generated constructor stub
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            View.MeasureSpec.getSize(widthMeasureSpec),
            View.MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {

        val w = measuredWidth
        val h = measuredHeight
        val r: Int
        if (w > h) {
            r = h / 2
        } else {
            r = w / 2
        }

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.color = Color.BLACK

        canvas.drawCircle((w / 2).toFloat(), (h / 2).toFloat(), r.toFloat(), paint)

        paint.color = Color.RED
        canvas.drawLine(
            (w / 2).toFloat(),
            (h / 2).toFloat(),
            (w / 2 + r * Math.sin((-direction).toDouble())).toFloat(),
            (h / 2 - r * Math.cos((-direction).toDouble())).toFloat(),
            paint
        )

    }

    fun update(dir: Float) {
        direction = dir
        invalidate()
    }

}