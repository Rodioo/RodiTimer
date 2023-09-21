package ui.timer.models

data class TimerConfiguration(
    var mainSeconds: Long = 25 * 60L,
    var shortBreakSeconds: Long = 5 * 60L,
    var longBreakSeconds: Long = 30 * 60L,
    var rounds: Long = 2L,
)
