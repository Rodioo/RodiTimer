import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.*
import menu.composables.AnimatedMenu
import timer.composables.Timer
import common.resources.TOP_BAR_HEIGHT
import common.resources.WINDOW_SIZE
import navigation.Screen
import settings.composables.Settings
import common.composables.TopBar

@Composable
@Preview
fun FrameWindowScope.App(
    resetWindowSize: () -> Unit,
    onMinimize: () -> Unit,
    onClose: () -> Unit,
) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Timer) }
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
            ) {

                when (currentScreen) {
                    is Screen.Timer -> Timer()
                    is Screen.Settings -> Settings()
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
