package ui.common

import java.time.LocalTime

fun Long.formatTime() : String = with(LocalTime.ofSecondOfDay(this)) {
    String.format("%02d:%02d:%02d", hour, minute, second)
}