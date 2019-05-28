package info.fitapp.chart.formatter

/**
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DefaultValueFormatter<T> : ValueFormatter<T> {

    override fun format(value: T): String {
        return value.toString()
    }

}
