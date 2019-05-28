package info.fitapp.chart.model

/**
 * Discrete label, continuous data point.
 *
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DataPoint(val value: Float, val label: String) {

    var comparisonValue = 0f

    init {
        comparisonValue = (value * (Math.random() + 0.3)).toFloat()
    }

}
