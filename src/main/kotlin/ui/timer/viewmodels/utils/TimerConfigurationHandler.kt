package ui.timer.viewmodels.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.timer.models.TimerConfiguration
import kotlin.math.roundToLong

private fun roundSliderValue(newSliderValue: Float, roundMargin : Int): Long {
    val newValue = newSliderValue.roundToLong()
    val roundedDifference = newValue % roundMargin

    return when(roundedDifference) {
        in 0L until (roundMargin / 2) -> newValue - roundedDifference
        else -> (newValue - roundedDifference) + roundMargin
    }

}

fun MutableStateFlow<TimerConfiguration>.onMainSliderChange(newSliderValue: Float) {
    val newValue = roundSliderValue(newSliderValue, 60)

    this.update { currentTimerConfiguration ->
        currentTimerConfiguration.copy(
            mainSeconds = newValue
        )
    }
}

fun MutableStateFlow<TimerConfiguration>.onShortBreakSliderChange(newSliderValue: Float) {
    val newValue = roundSliderValue(newSliderValue, 5)

    this.update { currentTimerConfiguration ->
        currentTimerConfiguration.copy(
            shortBreakSeconds = newValue
        )
    }
}

fun MutableStateFlow<TimerConfiguration>.onLongBreakSliderChange(newSliderValue: Float) {
    val newValue = roundSliderValue(newSliderValue, 30)

    this.update { currentTimerConfiguration ->
        currentTimerConfiguration.copy(
            longBreakSeconds = newValue
        )
    }
}

fun MutableStateFlow<TimerConfiguration>.onNumberOfRoundsSliderChange(newSliderValue: Float) {
    val newValue = newSliderValue.roundToLong()

    this.update { currentTimerConfiguration ->
        currentTimerConfiguration.copy(
            rounds = newValue
        )
    }
}

//fun MutableStateFlow<TimerConfiguration>.onSelectedTagChange(newTagId: Long) {
//    this.update { currentTimerConfiguration ->
//        currentTimerConfiguration.copy(
//            idTag = newTagId
//        )
//    }
//}
