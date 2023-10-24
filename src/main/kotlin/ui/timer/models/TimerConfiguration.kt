package ui.timer.models

import ui.tags.models.Tag

data class TimerConfiguration(
    val idTag: Long? = null,
    var mainSeconds: Long = TimerConfigurationDefaults.MAIN_SECONDS_DEFAULT.value,
    var shortBreakSeconds: Long = TimerConfigurationDefaults.SHORT_BREAK_SECONDS_DEFAULT.value,
    var longBreakSeconds: Long = TimerConfigurationDefaults.LONG_BREAK_SECONDS_DEFAULT.value,
    var rounds: Long = TimerConfigurationDefaults.ROUNDS_DEFAULT.value,
)
