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
import ui.home.composables.utils.SelectTagPopup
import ui.tags.models.Tag

@Composable
fun Home(
    isScreenDisabled: Boolean,
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val time by viewModel.time.collectAsState()
    val timerConfiguration by viewModel.timerConfiguration.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()

    var showSelectTagPopup by remember {
        mutableStateOf(false)
    }
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            CircularSlider(
                primaryColor = Color.Blue,
                secondaryColor = Color.Gray,
                backgroundColor = Color(0xFF2F384B),
                circleRadius = 200.dp,
                value = time,
                maxValue = timerConfiguration.mainSeconds,
                formattedValue = time.formatTime(),
                label = "asd_asd_asd_asd_asd_",
                onClickLabel = {
                    showSelectTagPopup = true
                },
                modifier = Modifier.size(400.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            PlayPauseButton(
                onClick = { viewModel.handlePlayPause() },
                isRunning = isTimerRunning,
                enabled = !isScreenDisabled
            )
        }

        if (showSelectTagPopup) {
            SelectTagPopup(
                listOf(
                    Tag(
                        label = "Ceva",
                        color = Color.Red
                    ),
                    Tag(
                        label = "Ceva2",
                        color = Color.Red
                    ),
                    Tag(
                        label = "Ceva3",
                        color = Color.Blue
                    ),
                    Tag(
                        label = "Ceva4",
                        color = Color.Yellow
                    ),
                    Tag(
                        label = "Ceva5",
                        color = Color.Blue
                    ),

                ),
                onClosePopup = {
                    showSelectTagPopup = false
                },
                modifier = Modifier.align(Alignment.Center).width(400.dp).height(300.dp)
            )
        }
    }
}


