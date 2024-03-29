package info.fitapp.chart.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import info.fitapp.chart.R
import info.fitapp.chart.model.DataSet
import kotlin.math.roundToInt


/**
 * @author Markus Deutsch @moopat
 */
class BarChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {

        const val RADIUS = 20f

        const val SPACING_TEXT_TO_BAR = 12f
        const val TOP_MARGIN = 0f
        const val BOTTOM_MARGIN = 10f // Required so that labels don't get cut.
        const val ANIMATION_DURATION = 800L
        const val COMPARISON_SHIFT = 0.1f

        const val NUMBER_OF_LABELS = 4

        private const val MINIMUM_INNER_MARGIN = 4f
        private const val MAXIMUM_INNER_MARGIN = 25f

    }

    private var gridColor = ContextCompat.getColor(context, R.color.barChartDefaultHorizontalGridColor)
    private var barStartColor = ContextCompat.getColor(context, R.color.barChartDefaultBarStartColor)
    private var barEndColor = ContextCompat.getColor(context, R.color.barChartDefaultBarEndColor)
    private var comparisonStartColor = ContextCompat.getColor(context, R.color.barChartDefaultComparisonStart)
    private var comparisonEndColor = ContextCompat.getColor(context, R.color.barChartDefaultComparisonEnd)
    private var labelColor = ContextCompat.getColor(context, R.color.barChartDefaultLabelColor)
    private var labelSize = context.resources.getDimension(R.dimen.barChartDefaultLabelSize)


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BarChartView,
            0, 0
        ).apply {

            try {
                gridColor = getColor(R.styleable.BarChartView_gridColor, gridColor)
                barStartColor = getColor(R.styleable.BarChartView_barStartColor, barStartColor)
                barEndColor = getColor(R.styleable.BarChartView_barEndColor, barEndColor)
                comparisonStartColor = getColor(R.styleable.BarChartView_comparisonStartColor, comparisonStartColor)
                comparisonEndColor = getColor(R.styleable.BarChartView_comparisonEndColor, comparisonEndColor)
                labelColor = getColor(R.styleable.BarChartView_labelColor, labelColor)
                labelSize = getDimension(R.styleable.BarChartView_labelSize, labelSize)
            } finally {
                recycle()
            }
        }
    }

    private var showComparisonIfAvailable = true
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
    }

    private val comparisonPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val xAxisLabelPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = labelColor
        textSize = labelSize
        textAlign = Paint.Align.CENTER
    }

    private val yAxisLabelPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = labelColor
        textSize = labelSize
        textAlign = Paint.Align.RIGHT
    }

    private val horizontalLinePaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = gridColor
        strokeWidth = 2f
    }

    private val barAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener {
            animatedScale = it.animatedValue as Float
        }
        duration = ANIMATION_DURATION
        interpolator = DecelerateInterpolator()
    }

    fun setShowComparisonIfAvailable(show: Boolean) {
        if (show != showComparisonIfAvailable) {
            showComparisonIfAvailable = show
            invalidate()
        }
    }

    private fun getInnerMargin(): Float {
        val minThreshold = 25 // Starting at this value, the min applies.
        val size = dataSet?.getSize() ?: 0

        val range = MAXIMUM_INNER_MARGIN - MINIMUM_INNER_MARGIN
        val scaleFactor = Math.min(1f, size.toFloat() / minThreshold.toFloat())
        return MINIMUM_INNER_MARGIN + range * (1 - scaleFactor)
    }

    private fun getHorizontalLabelStepSize(): Int {
        val size = dataSet?.getSize() ?: 0
        return Math.ceil(size.toFloat().div(10).toDouble()).toInt()
    }

    fun setDataSet(set: DataSet) {
        //barAnimator?.cancel()
        if (this.dataSet == set) {
            return
        }
        this.dataSet = set
        barAnimator.start()
        invalidate()
    }

    fun setTypeface(typeface: Typeface) {
        xAxisLabelPaint.typeface = typeface
        yAxisLabelPaint.typeface = typeface
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
            barStartColor,
            barEndColor,
            Shader.TileMode.CLAMP
        )

        comparisonPaint.shader = LinearGradient(
            0f,
            0f,
            0f,
            h.toFloat(),
            comparisonStartColor,
            comparisonEndColor,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val data = dataSet
        val maxValue = if (showComparisonIfAvailable) data?.getMaxValue() else data?.getPrimaryMaxValue()
        if (data == null || maxValue == null || data.getSize() <= 0 || maxValue <= 0) return

        canvas?.apply {

            val stepSize = data.stepMaker.getStepSize(NUMBER_OF_LABELS, maxValue)
            var maxTextWidth = 0f

            // Iterate twice: once to get all text widths, once to paint.
            for (numericLabel in stepSize..maxValue.roundToInt() step stepSize) {
                val label = data.valueFormatter.format(numericLabel.toFloat())
                maxTextWidth = Math.max(maxTextWidth, yAxisLabelPaint.measureText(label))
            }

            // Now paint!
            for (numericLabel in stepSize..maxValue.roundToInt() step stepSize) {
                val topOffset = paddingTop.toFloat() + TOP_MARGIN
                val bottomOffset =
                    paddingBottom.toFloat() + BOTTOM_MARGIN + labelSize + SPACING_TEXT_TO_BAR
                val maxBarHeight = totalHeight - (topOffset + bottomOffset)

                val scaleFactor = numericLabel.toFloat().div(maxValue)
                val height = maxBarHeight * scaleFactor
                val pos = topOffset + (maxBarHeight - height)

                drawLine(paddingStart.toFloat(), pos, totalWidth - paddingEnd.toFloat(), pos, horizontalLinePaint)

                val label = data.valueFormatter.format(numericLabel.toFloat())

                drawText(
                    label,
                    paddingStart.toFloat() + maxTextWidth,
                    pos + labelSize + 10f, // TODO: Move 5f to Margin
                    yAxisLabelPaint
                )


            }

            val leftChartMargin = paddingLeft + maxTextWidth + SPACING_TEXT_TO_BAR
            val availableWidthForBars = availableWidth - (maxTextWidth + SPACING_TEXT_TO_BAR)

            val totalWithPerPoint =
                (availableWidthForBars - ((data.getSize() - 1) * getInnerMargin())).div(data.getSize())
            val widthPerBar =
                if (showComparisonIfAvailable && data.hasComparisonData()) totalWithPerPoint * (1 - COMPARISON_SHIFT) else totalWithPerPoint

            val comparisonGap = totalWithPerPoint * COMPARISON_SHIFT

            data.items.forEachIndexed { index, point ->

                val topOffset = paddingTop.toFloat() + TOP_MARGIN
                val bottomOffset =
                    paddingBottom.toFloat() + BOTTOM_MARGIN + labelSize + SPACING_TEXT_TO_BAR

                val maxBarHeight = totalHeight - (topOffset + bottomOffset)
                val scaleFactor = point.value.div(maxValue)
                val height = maxBarHeight * scaleFactor * animatedScale
                val comparisonHeight = maxBarHeight * point.comparisonValue.div(maxValue) * animatedScale

                val startPosition = leftChartMargin + index * (totalWithPerPoint + getInnerMargin())

                val calculatedTop = topOffset + (maxBarHeight - height)
                val calculatedBottom = totalHeight - bottomOffset

                val calculatedRight = startPosition + widthPerBar
                val calculatedLeft = startPosition

                val comparisonTop = topOffset + (maxBarHeight - comparisonHeight)

                // Draw comparison
                if (showComparisonIfAvailable) {
                    drawRoundRect(
                        calculatedLeft + comparisonGap,
                        comparisonTop,
                        calculatedRight + comparisonGap,
                        calculatedBottom,
                        RADIUS,
                        RADIUS,
                        comparisonPaint
                    )
                }

                // Draw value
                drawRoundRect(
                    calculatedLeft,
                    calculatedTop,
                    calculatedRight,
                    calculatedBottom,
                    RADIUS,
                    RADIUS,
                    barPaint
                )

                if (index % getHorizontalLabelStepSize() == 0) {
                    val comparisonSpace = if (data.hasComparisonData()) comparisonGap else 0f
                    drawText(
                        data.labelFormatter.format(point.label),
                        (calculatedLeft + calculatedRight + comparisonSpace) / 2,
                        totalHeight - (BOTTOM_MARGIN + paddingBottom.toFloat()),
                        xAxisLabelPaint
                    )
                }

            }

        }

    }
}
