package ui

import ui.about.composables.About
import ui.alarm.composables.Alarm
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.*
import repository.DriverFactory
import repository.LocalDatabase
import repository.dao.timerConfiguration.TimerConfigurationDaoImpl
import ui.menu.composables.AnimatedMenu
import ui.home.composables.Home
import ui.common.resources.TOP_BAR_HEIGHT
import ui.common.resources.WINDOW_SIZE
import ui.navigation.Screen
import ui.settings.composables.Settings
import ui.common.composables.TopBar
import ui.common.resources.BACKGROUND_COLOR
import ui.tags.composables.Tags
import ui.timer.composables.Timer
import ui.timer.models.TimerConfiguration

@Composable
fun FrameWindowScope.App(
    resetWindowSize: () -> Unit,
    onMinimize: () -> Unit,
    onClose: () -> Unit,
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var isVisible by remember { mutableStateOf(false) }

    MaterialTheme {
        Column {
            TopBar(
                resetWindowSize = resetWindowSize,
                onClickMenu = { isVisible = !isVisible },
                onMinimize = onMinimize,
                onClose = onClose
            )
            Box(
                modifier = Modifier
                    .height(WINDOW_SIZE.height - TOP_BAR_HEIGHT)
                    .width(WINDOW_SIZE.width)
                    .background(BACKGROUND_COLOR)
            ) {

                when (currentScreen) {
                    is Screen.Home -> Home()
                    is Screen.Timer -> Timer()
                    is Screen.Alarm -> Alarm()
                    is Screen.Tags -> Tags()
                    is Screen.Settings -> Settings()
                    is Screen.About -> About()
                }

                AnimatedMenu(
                    isVisible,
                    onSelectMenuItem = { menuItem ->
                        currentScreen = menuItem.screen
                    },
                    onCloseMenu = {
                        isVisible = false
                    }
                )
            }
        }
    }
}

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = WINDOW_SIZE
    )

    Window(
        undecorated = true,
        state = windowState,
        resizable = false,
        onCloseRequest = ::exitApplication,
    ) {
        App(
            resetWindowSize = { windowState.size = WINDOW_SIZE },
            onMinimize = { windowState.isMinimized = windowState.isMinimized.not() },
            onClose = ::exitApplication
        )
    }
}
