package ui.timer.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ui.timer.models.TimerConfiguration
import ui.timer.models.TimerConfigurationType
import ui.timer.viewmodels.utils.onLongBreakSliderChange
import ui.timer.viewmodels.utils.onMainSliderChange
import ui.timer.viewmodels.utils.onNumberOfRoundsSliderChange
import ui.timer.viewmodels.utils.onShortBreakSliderChange

class TimerViewModel {

    private val _timerConfiguration = MutableStateFlow(TimerConfiguration())
        val timerConfiguration = _timerConfiguration.asStateFlow()

    fun updateTimerConfiguration(timerConfigurationType: TimerConfigurationType, newSliderValue: Float) {
        when (timerConfigurationType) {
            TimerConfigurationType.MAIN -> _timerConfiguration.onMainSliderChange(newSliderValue)
            TimerConfigurationType.SHORT_BREAK -> _timerConfiguration.onShortBreakSliderChange(newSliderValue)
            TimerConfigurationType.LONG_BREAK -> _timerConfiguration.onLongBreakSliderChange(newSliderValue)
            TimerConfigurationType.ROUNDS -> _timerConfiguration.onNumberOfRoundsSliderChange(newSliderValue)
        }
    }

    fun resetTimerConfiguration() {
        _timerConfiguration.value = TimerConfiguration()
    }
}