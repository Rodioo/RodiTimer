package timer.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import common.formatTime
import timer.composables.utils.TimerSlider
import timer.models.TimerConfigurationType
import timer.viewmodels.TimerViewModel

@Preview
@Composable
fun Timer(
    viewModel: TimerViewModel = remember { TimerViewModel() }
) {
    val timerConfiguration by viewModel.timerConfiguration.collectAsState()

    Column(
      modifier = Modifier.fillMaxSize()
    ) {
        TimerSlider(
            label = TimerConfigurationType.FOCUS.label,
            value = timerConfiguration.focusSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.FOCUS, newSliderValue)
            },
            valueRange = TimerConfigurationType.FOCUS.valueRange,
            formatValueFunction = Long::formatTime
        )

        TimerSlider(
            label = TimerConfigurationType.SHORT_BREAK.label,
            value = timerConfiguration.shortBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.SHORT_BREAK, newSliderValue)
            },
            valueRange = TimerConfigurationType.SHORT_BREAK.valueRange,
            formatValueFunction = Long::formatTime
        )

        TimerSlider(
            label = TimerConfigurationType.LONG_BREAK.label,
            value = timerConfiguration.longBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.LONG_BREAK, newSliderValue)
            },
            valueRange = TimerConfigurationType.LONG_BREAK.valueRange,
            formatValueFunction = Long::formatTime
        )

        TimerSlider(
            label = TimerConfigurationType.ROUNDS.label,
            value = timerConfiguration.rounds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.ROUNDS, newSliderValue)
            },
            valueRange = TimerConfigurationType.ROUNDS.valueRange
        )
    }
}