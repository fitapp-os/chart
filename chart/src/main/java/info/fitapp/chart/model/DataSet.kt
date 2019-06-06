package info.fitapp.chart.model

import info.fitapp.chart.formatter.DefaultLabelFormatter
import info.fitapp.chart.formatter.DefaultValueFormatter
import info.fitapp.chart.formatter.LabelFormatter
import info.fitapp.chart.formatter.ValueFormatter
import info.fitapp.chart.steps.PrettyStepMaker
import info.fitapp.chart.steps.StepMaker

/**
 * Discrete labels, continuous values.
 * @author Markus Deutsch @moopat
 */
open class DataSet {

    val items = mutableListOf<DataPoint>()
    var valueFormatter: ValueFormatter<Float> = DefaultValueFormatter()
    var labelFormatter: LabelFormatter = DefaultLabelFormatter()
    var stepMaker: StepMaker = PrettyStepMaker()

    fun getSize() = items.size

    fun getPrimaryMaxValue() = items.maxBy { it.value }?.value
    fun getPrimaryMinValue() = items.minBy { it.value }?.value

    fun getComparisonMaxValue() = items.maxBy { it.comparisonValue }?.comparisonValue
    fun getComparisonMinValue() = items.minBy { it.comparisonValue }?.comparisonValue

    fun getMaxValue(): Float {
        val primaryValue = getPrimaryMaxValue()
        val comparisonValue = getComparisonMaxValue()

        return if (primaryValue == null && comparisonValue == null) {
            0f
        } else if (primaryValue != null && comparisonValue != null) {
            Math.max(primaryValue, comparisonValue)
        } else if (primaryValue != null) {
            primaryValue
        } else {
            comparisonValue!!
        }
    }

    fun getMinValue(): Float {
        val primaryValue = getPrimaryMinValue()
        val comparisonValue = getComparisonMinValue()

        return if (primaryValue == null && comparisonValue == null) {
            0f
        } else if (primaryValue != null && comparisonValue != null) {
            Math.max(primaryValue, comparisonValue)
        } else if (primaryValue != null) {
            primaryValue
        } else {
            comparisonValue!!
        }
    }

    fun hasData(): Boolean {
        return hasPrimaryData() || hasComparisonData()
    }

    fun hasPrimaryData(): Boolean {
        return getPrimaryMaxValue() ?: 0f > 0
    }

    fun hasComparisonData(): Boolean {
        return getComparisonMaxValue() ?: 0f > 0
    }

    fun matches(other: DataSet): Boolean {
        if (items.size != other.items.size) return false
        items.forEachIndexed { index, dataPoint ->
            if (dataPoint != other.items[index]) {
                return false
            }
        }
        return true
    }

}
