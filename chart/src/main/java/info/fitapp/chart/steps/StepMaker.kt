package info.fitapp.chart.steps

/**
 * @author Markus Deutsch @moopat
 */
abstract class StepMaker {

    protected abstract fun calculateStepSize(maxStepCount: Int, maxValue: Float): Int

    fun getStepSize(maxStepCount: Int, maxValue: Float): Int {
        return Math.max(1, calculateStepSize(maxStepCount, maxValue))
    }

}
