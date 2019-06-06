package info.fitapp.chart.formatter

/**
 * @author Markus Deutsch @moopat
 */
interface ValueFormatter<T> {

    fun format(value: T): String

}
