package info.fitapp.chart.widget

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import info.fitapp.chart.R


/**
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class BarChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {

        const val RADIUS = 10f
        const val INNER_MARGIN = 20f

    }

    private var data = listOf(10, 20, 30, 40, 45, 35, 25)

    private var availableHeight = 0
    private var availableWidth = 0

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        textSize = 10f
        shader = LinearGradient(
            0f,
            0f,
            0f,
            1000f, // TODO: Make flexible.
            ContextCompat.getColor(context, R.color.gradientStart),
            ContextCompat.getColor(context, R.color.gradientEnd),
            Shader.TileMode.CLAMP
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        availableHeight = h - (paddingTop + paddingBottom)
        availableWidth = w - (paddingStart + paddingEnd)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)



        canvas?.apply {

            val widthPerBar = (availableWidth - ((data.size - 1) * INNER_MARGIN)).div(data.size)
            val maxValue = data.max()!!

            data.forEachIndexed { index, value ->

                val scaleFactor = value.toFloat().div(maxValue) // TODO: Handle maxvalue = 0
                val height = availableHeight * scaleFactor

                val startPosition = paddingLeft + index * (widthPerBar + INNER_MARGIN)
                val totalHeight = availableHeight + paddingTop.toFloat() + paddingBottom.toFloat()

                val calculatedTop = paddingTop.toFloat() + (availableHeight - height)
                val calculatedBottom = totalHeight - paddingBottom.toFloat()

                Log.d("BarChartView", "[$index, $value] Top: $calculatedTop, Bottom: $calculatedBottom")

                drawRoundRect(
                    startPosition,
                    calculatedTop,
                    startPosition + widthPerBar,
                    calculatedBottom,
                    RADIUS,
                    RADIUS,
                    barPaint
                )
            }


        }

        /**
         *
         *      * Draw the specified round-rect using the specified paint. The roundrect will be filled or
         * framed based on the Style in the paint.
         *
         * @param rx The x-radius of the oval used to round the corners
         * @param ry The y-radius of the oval used to round the corners
         * @param paint The paint used to draw the roundRect
        public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry,
        @NonNull Paint paint) {
        super.drawRoundRect(left, top, right, bottom, rx, ry, paint);
        }

         */
    }
}
