package timer.models

import kotlin.math.roundToLong

data class TimerConfiguration(
    var focusSeconds: Long = TimerConfigurationType.FOCUS.valueRange.start.roundToLong(),
    var shortBreakSeconds: Long = TimerConfigurationType.SHORT_BREAK.valueRange.start.roundToLong(),
    var longBreakSeconds: Long = TimerConfigurationType.LONG_BREAK.valueRange.start.roundToLong(),
    var rounds: Long = TimerConfigurationType.ROUNDS.valueRange.start.roundToLong(),
)
