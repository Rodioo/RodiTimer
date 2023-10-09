package ui.timer.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.common.composables.StyledButton
import ui.common.formatTime
import ui.timer.composables.utils.TimerSlider
import ui.timer.models.TimerConfigurationType
import ui.timer.viewmodels.TimerViewModel
import ui.common.resources.*

//@TODO: Tie these tags with the home screen and the timer screen (the label changes and the slider color changes accordingly to the tag)
@Composable
fun Timer(
    viewModel: TimerViewModel = remember { TimerViewModel() }
) {
    val timerConfiguration by viewModel.timerConfiguration.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        TimerSlider(
            label = TimerConfigurationType.MAIN.label,
            value = timerConfiguration.mainSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.MAIN, newSliderValue)
            },
            onValueChangeFinished = {
                viewModel.saveCurrentTimerConfiguration()
            },
            valueRange = TimerConfigurationType.MAIN.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = MAIN_SLIDER_COLOR,
                activeTrackColor = MAIN_SLIDER_COLOR,
                inactiveTrackColor = DIVIDER_COLOR
            )
        )

        TimerSlider(
            label = TimerConfigurationType.SHORT_BREAK.label,
            value = timerConfiguration.shortBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.SHORT_BREAK, newSliderValue)
            },
            onValueChangeFinished = {
                viewModel.saveCurrentTimerConfiguration()
            },
            valueRange = TimerConfigurationType.SHORT_BREAK.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = SHORT_BREAK_SLIDER_COLOR,
                activeTrackColor = SHORT_BREAK_SLIDER_COLOR,
                inactiveTrackColor = DIVIDER_COLOR
            )
        )

        TimerSlider(
            label = TimerConfigurationType.LONG_BREAK.label,
            value = timerConfiguration.longBreakSeconds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.LONG_BREAK, newSliderValue)
            },
            onValueChangeFinished = {
                viewModel.saveCurrentTimerConfiguration()
            },
            valueRange = TimerConfigurationType.LONG_BREAK.valueRange,
            formatValueFunction = Long::formatTime,
            colors = SliderDefaults.colors(
                thumbColor = LONG_BREAK_SLIDER_COLOR,
                activeTrackColor = LONG_BREAK_SLIDER_COLOR,
                inactiveTrackColor = DIVIDER_COLOR
            )
        )

        TimerSlider(
            label = TimerConfigurationType.ROUNDS.label,
            value = timerConfiguration.rounds,
            onValueChange = {newSliderValue ->
                viewModel.updateTimerConfiguration(TimerConfigurationType.ROUNDS, newSliderValue)
            },
            onValueChangeFinished = {
                viewModel.saveCurrentTimerConfiguration()
            },
            valueRange = TimerConfigurationType.ROUNDS.valueRange,
            colors = SliderDefaults.colors(
                thumbColor = ROUNDS_SLIDER_COLOR,
                activeTrackColor = ROUNDS_SLIDER_COLOR,
                inactiveTrackColor = DIVIDER_COLOR
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        StyledButton(
            onClick = {
                viewModel.resetTimerConfiguration()
            },
            text = "Reset to default",
            leadingIcon = Icons.Default.RestartAlt,
            backgroundColor = BUTTON_RED_BACKGROUND_COLOR,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}