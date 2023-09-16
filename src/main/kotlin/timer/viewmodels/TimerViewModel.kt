package timer.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timer.models.TimerConfiguration
import timer.models.TimerConfigurationType
import timer.viewmodels.utils.*

class TimerViewModel {

    private val _timerConfiguration = MutableStateFlow(TimerConfiguration())
        val timerConfiguration = _timerConfiguration.asStateFlow()

    fun updateTimerConfiguration(timerConfigurationType: TimerConfigurationType,  newSliderValue: Float) {
        when (timerConfigurationType) {
            TimerConfigurationType.FOCUS -> _timerConfiguration.onFocusSliderChange(newSliderValue)
            TimerConfigurationType.SHORT_BREAK -> _timerConfiguration.onShortBreakSliderChange(newSliderValue)
            TimerConfigurationType.LONG_BREAK -> _timerConfiguration.onLongBreakSliderChange(newSliderValue)
            TimerConfigurationType.ROUNDS -> _timerConfiguration.onNumberOfRoundsSliderChange(newSliderValue)
        }
    }
}