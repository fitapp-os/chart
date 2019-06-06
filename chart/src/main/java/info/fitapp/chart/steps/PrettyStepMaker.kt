package info.fitapp.chart.steps

import kotlin.math.roundToInt

/**
 * Tries to make 10er, 100er, 1000er,... steps, based on the max number.
 * @author Markus Deutsch @moopat
 */
class PrettyStepMaker : StepMaker() {

    override fun calculateStepSize(maxStepCount: Int, maxValue: Float): Int {
        val simpleThreshold = maxValue.div(maxStepCount).toInt()
        val thresholdNumberCount = simpleThreshold.toString().length

        val factor = Math.pow(10.0, thresholdNumberCount.minus(1).toDouble())
        return Math.max(1, maxValue.div(maxStepCount).div(factor).roundToInt().times(factor).toInt())
    }

}
