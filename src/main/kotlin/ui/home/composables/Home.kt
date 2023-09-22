package ui.home.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.home.viewmodels.HomeViewModel
import ui.home.composables.utils.PlayPauseButton
import ui.common.composables.CircularSlider
import ui.common.formatTime

@Composable
fun Home(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val time by viewModel.time.collectAsState()
    val timerConfiguration by viewModel.timerConfiguration.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CircularSlider(
            primaryColor = Color.Blue,
            secondaryColor = Color.Gray,
            backgroundColor = Color(0xFF2F384B),
            circleRadius = 175f,
            value = time,
            maxValue = timerConfiguration.mainSeconds,
            formattedValue = time.formatTime(),
            modifier = Modifier.size(350.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PlayPauseButton(
            isRunning = isTimerRunning,
            onClick = { viewModel.handlePlayPause() }
        )

    }
}


