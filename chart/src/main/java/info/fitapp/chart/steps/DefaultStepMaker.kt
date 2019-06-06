package info.fitapp.chart.steps

import kotlin.math.roundToInt

/**
 * Just divides the max number by the step count and performs some rounding.
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DefaultStepMaker : StepMaker() {

    override fun calculateStepSize(maxStepCount: Int, maxValue: Float): Int {
        return maxValue.div(maxStepCount).roundToInt()
    }

}
