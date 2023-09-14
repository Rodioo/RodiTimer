package timer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import timer.viewmodels.TimerViewModel
import timer.composables.utils.PlayPauseButton
import common.composables.CircularSlider
import common.formatTime

@Composable
fun Timer(
    viewModel: TimerViewModel = remember { TimerViewModel() }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color(0xFF2F384B))
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CircularSlider(
            primaryColor = Color.Blue,
            secondaryColor = Color.Gray,
            backgroundColor = Color(0xFF2F384B),
            circleRadius = 175f,
            value = viewModel.startTime,
            maxValue = 10L,
            formattedValue = viewModel.startTime.formatTime(),
            modifier = Modifier.size(350.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PlayPauseButton(
            isRunning = viewModel.isTimerRunning,
            onClick = { viewModel.handlePlayPause() }
        )

    }
}


