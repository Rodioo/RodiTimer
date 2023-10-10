package ui.common

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime

fun Long.formatTime() : String = with(LocalTime.ofSecondOfDay(this)) {
    String.format("%02d:%02d:%02d", hour, minute, second)
}

fun String.toColor() : Color? {
    if (!this.startsWith("Color")) {
        return null
    }

    try {
        val colorValues = this
            .replace("(", "")
            .replace(")", "")
            .split("Color")[1]
            .replace(" ", "")
            .split(",")

        return Color(
            red = colorValues[0].toFloat(),
            green = colorValues[1].toFloat(),
            blue = colorValues[2].toFloat(),
        )
    } catch(exception: Exception) {
        return null

    }
}

fun <T> debounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope,
    onChange: (T) -> Unit,
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(waitMs)
            onChange(param)
        }
    }
}

//TODO: implement this
fun getRandomColor(): Color {
    return Color(
        red = (0..256).random(),
        green = (0..256).random(),
        blue = (0..256).random(),
    )
}