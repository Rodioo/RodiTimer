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

@Composable
fun Home(
    isScreenDisabled: Boolean,
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val time by viewModel.time.collectAsState()
    val tags by viewModel.tags.collectAsState()
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
                tag = viewModel.getTagInfo(),
                secondaryColor = Color.Gray,
                backgroundColor = Color(0xFF2F384B),
                circleRadius = 200.dp,
                value = time,
                maxValue = timerConfiguration.mainSeconds,
                formattedValue = time.formatTime(),
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
                tags = tags,
                onSelectTag = {
                    viewModel.updateTimerConfigurationTag(it)
                },
                onClosePopup = {
                    showSelectTagPopup = false
                },
                modifier = Modifier.align(Alignment.Center).width(400.dp).height(300.dp)
            )
        }
    }
}


