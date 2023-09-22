package ui.timer.models

data class TimerConfiguration(
    var mainSeconds: Long = TimerConfigurationDefaults.MAIN_SECONDS_DEFAULT.value,
    var shortBreakSeconds: Long = TimerConfigurationDefaults.SHORT_BREAK_SECONDS_DEFAULT.value,
    var longBreakSeconds: Long = TimerConfigurationDefaults.LONG_BREAK_SECONDS_DEFAULT.value,
    var rounds: Long = TimerConfigurationDefaults.ROUNDS_DEFAULT.value,
)
