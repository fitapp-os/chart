package info.fitapp.chart.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import info.fitapp.chart.R
import info.fitapp.chart.model.DataSet
import kotlin.math.roundToInt


/**
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class BarChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {

        const val RADIUS = 20f
        const val INNER_MARGIN = 25f
        const val TEXT_SIZE = 40f

        const val SPACING_TEXT_TO_BAR = 12f
        const val TOP_MARGIN = 0f
        const val BOTTOM_MARGIN = 0f
        const val LEFT_BAR_MARGIN = 100f
        const val ANIMATION_DURATION = 800L

        const val NUMBER_OF_LABELS = 5

    }

    private var totalHeight = 0
    private var totalWidth = 0
    private var availableHeight = 0
    private var availableWidth = 0
    private var animatedScale = 1f
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    private var dataSet: DataSet? = null

    private val barPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        textSize = TEXT_SIZE
    }

    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.labelColor)
        textSize = TEXT_SIZE
        textAlign = Paint.Align.CENTER
    }

    private val horizontalLinePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.horizontalGridColor)
        strokeWidth = 2f
    }

    private val barAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener {
            animatedScale = it.animatedValue as Float
        }
        duration = ANIMATION_DURATION
        interpolator = DecelerateInterpolator()
    }

    fun setDataSet(set: DataSet) {
        //barAnimator?.cancel()
        this.dataSet = set
        barAnimator.start()
        invalidate()
    }

    fun setTypeface(typeface: Typeface) {
        textPaint.typeface = typeface
        invalidate()
    }

    override fun onDetachedFromWindow() {
        barAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        totalHeight = h
        totalWidth = w
        availableHeight = h - (paddingTop + paddingBottom)
        availableWidth = w - (paddingStart + paddingEnd)

        barPaint.shader = LinearGradient(
            0f,
            0f,
            0f,
            h.toFloat(),
            ContextCompat.getColor(context, R.color.gradientStart),
            ContextCompat.getColor(context, R.color.gradientEnd),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val data = dataSet
        if (data == null || data.getSize() <= 0) return

        canvas?.apply {

            val leftChartMargin = paddingLeft + LEFT_BAR_MARGIN
            val availableWidthForBars = availableWidth - LEFT_BAR_MARGIN
            val widthPerBar = (availableWidthForBars - ((data.getSize() - 1) * INNER_MARGIN)).div(data.getSize())
            val maxValue = data.getMaxValue()!!

            // Generate intervals
            val stepSize = maxValue.div(NUMBER_OF_LABELS).toInt()

            for (numericLabel in stepSize..maxValue.roundToInt() step stepSize) {
                val topOffset = paddingTop.toFloat() + TOP_MARGIN
                val bottomOffset =
                    paddingBottom.toFloat() + BOTTOM_MARGIN + TEXT_SIZE + SPACING_TEXT_TO_BAR
                val maxBarHeight = totalHeight - (topOffset + bottomOffset)

                val scaleFactor = numericLabel.toFloat().div(maxValue) // TODO: Handle maxvalue = 0
                val height = maxBarHeight * scaleFactor
                val pos = topOffset + (maxBarHeight - height)

                drawLine(paddingStart.toFloat(), pos, totalWidth - paddingEnd.toFloat(), pos, horizontalLinePaint)

                drawText(
                    numericLabel.toString(),
                    paddingStart.toFloat() + LEFT_BAR_MARGIN.div(2),
                    pos + TEXT_SIZE + 10f, // TODO: Move 5f to Margin
                    textPaint
                )
            }

            data.items.forEachIndexed { index, point ->

                val topOffset = paddingTop.toFloat() + TOP_MARGIN
                val bottomOffset =
                    paddingBottom.toFloat() + BOTTOM_MARGIN + TEXT_SIZE + SPACING_TEXT_TO_BAR

                val maxBarHeight = totalHeight - (topOffset + bottomOffset)
                val scaleFactor = point.value.div(maxValue) // TODO: Handle maxvalue = 0
                val height = maxBarHeight * scaleFactor * animatedScale

                val startPosition = leftChartMargin + index * (widthPerBar + INNER_MARGIN)

                val calculatedTop = topOffset + (maxBarHeight - height)
                val calculatedBottom = totalHeight - bottomOffset

                val calculatedRight = startPosition + widthPerBar
                val calculatedLeft = startPosition

                drawRoundRect(
                    calculatedLeft,
                    calculatedTop,
                    calculatedRight,
                    calculatedBottom,
                    RADIUS,
                    RADIUS,
                    barPaint
                )

                drawText(
                    point.label,
                    (calculatedLeft + calculatedRight) / 2,
                    totalHeight - (BOTTOM_MARGIN + paddingBottom.toFloat()),
                    textPaint
                )
            }

        }

    }
}
