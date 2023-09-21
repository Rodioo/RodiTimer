package ui.timer.models

enum class TimerConfigurationType(
    val label: String,
    val valueRange: ClosedFloatingPointRange<Float>
) {
    MAIN("Focus", 60f..2 * 60 * 60f),
    SHORT_BREAK("Short break", 0f..12 * 60f),
    LONG_BREAK("Long break", 0f..60 * 60f),
    ROUNDS("Rounds", 1f..11f) //max rounds is 10, 11 represents unlimited rounds
}