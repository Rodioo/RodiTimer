package ui.timer.models

enum class TimerConfigurationDefaults(val value: Long) {
    MAIN_SECONDS_DEFAULT(25 * 60L),
    SHORT_BREAK_SECONDS_DEFAULT(5 * 60L),
    LONG_BREAK_SECONDS_DEFAULT(30 * 60L),
    ROUNDS_DEFAULT(1),
}
