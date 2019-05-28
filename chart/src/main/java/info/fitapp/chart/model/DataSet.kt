package info.fitapp.chart.model

/**
 * Discrete labels, continuous values.
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DataSet {

    val items = mutableListOf<DataPoint>()

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

}
