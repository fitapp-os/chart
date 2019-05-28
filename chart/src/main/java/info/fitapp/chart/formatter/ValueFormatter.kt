package info.fitapp.chart.formatter

/**
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
interface ValueFormatter<T> {

    fun format(value: T): String

}
