package info.fitapp.chart.formatter

/**
 * @author Markus Deutsch @moopat
 */
class DefaultValueFormatter<T> : ValueFormatter<T> {

    override fun format(value: T): String {
        return value.toString()
    }

}
