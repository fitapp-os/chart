package info.fitapp.chart.steps

import kotlin.math.roundToInt

/**
 * Tries to make steps in full minutes, tens-of-minutes, hours (depending on the max value).
 * @author Markus Deutsch <markus.deutsch@fitapp.info>
 */
class DurationStepMaker : StepMaker() {

    override fun calculateStepSize(maxStepCount: Int, maxValue: Float): Int {
        // The maxValue is in seconds!

        val simpleThreshold = maxValue.div(maxStepCount).toInt()

        return Math.max(
            Math.min(600, maxValue.roundToInt()).toDouble(),
            simpleThreshold.div(600f).toInt().times(600f).toDouble()
        ).toInt()
    }

}