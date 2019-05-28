package info.fitapp.chart.model

/**
 * Discrete labels, continuous values.
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DataSet {

    val items = mutableListOf<DataPoint>()

    fun getSize() = items.size

    fun getMaxValue() = items.maxBy { it.value }?.value
    fun getMinValue() = items.minBy { it.value }?.value

}
