package info.fitapp.chart.model

/**
 * Discrete label, continuous data point.
 *
 * @author Markus Deutsch @moopat
 */
class DataPoint(val value: Float, val label: String) {

    var comparisonValue = 0f

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataPoint

        if (value != other.value) return false
        if (label != other.label) return false
        if (comparisonValue != other.comparisonValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + comparisonValue.hashCode()
        return result
    }

}
