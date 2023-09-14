package common

import java.util.concurrent.TimeUnit

fun Long.formatTime(): String = String.format(
    "%02d:%02d:%02d",
    TimeUnit.SECONDS.toHours(this) % 60 % 60,
    TimeUnit.SECONDS.toMinutes(this) % 60,
    TimeUnit.SECONDS.toSeconds(this)
)