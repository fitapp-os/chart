package info.fitapp.chart.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

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

            data.forEachIndexed { index, value ->
                val startPosition = paddingLeft + index * (widthPerBar + INNER_MARGIN)
                drawRoundRect(
                    startPosition,
                    paddingBottom.toFloat(),
                    startPosition + widthPerBar,
                    paddingTop.toFloat() + availableHeight,
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
