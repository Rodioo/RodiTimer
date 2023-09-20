package timer.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.composables.ResetToDefaultButton
import common.formatTime
import common.resources.*
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
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().padding(
            24.dp
        )
    ) {
        TimerSlider(
            label = TimerConfigurationType.FOCUS.label,
            value = timerConfiguration.focusSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.FOCUS, newSliderValue)
            },
            valueRange = TimerConfigurationType.FOCUS.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = FOCUS_SLIDER_COLOR,
                activeTrackColor = FOCUS_SLIDER_COLOR,
                inactiveTrackColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.3f)
            )
        )

        TimerSlider(
            label = TimerConfigurationType.SHORT_BREAK.label,
            value = timerConfiguration.shortBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.SHORT_BREAK, newSliderValue)
            },
            valueRange = TimerConfigurationType.SHORT_BREAK.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = SHORT_BREAK_SLIDER_COLOR,
                activeTrackColor = SHORT_BREAK_SLIDER_COLOR,
                inactiveTrackColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.3f)
            )
        )

        TimerSlider(
            label = TimerConfigurationType.LONG_BREAK.label,
            value = timerConfiguration.longBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.LONG_BREAK, newSliderValue)
            },
            valueRange = TimerConfigurationType.LONG_BREAK.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = LONG_BREAK_SLIDER_COLOR,
                activeTrackColor = LONG_BREAK_SLIDER_COLOR,
                inactiveTrackColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.3f)
            )
        )

        TimerSlider(
            label = TimerConfigurationType.ROUNDS.label,
            value = timerConfiguration.rounds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.ROUNDS, newSliderValue)
            },
            valueRange = TimerConfigurationType.ROUNDS.valueRange,
            colors = SliderDefaults.colors(
                thumbColor = ROUNDS_SLIDER_COLOR,
                activeTrackColor = ROUNDS_SLIDER_COLOR,
                inactiveTrackColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.3f)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResetToDefaultButton(
            onClick = {
                viewModel.resetTimerConfiguration()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}