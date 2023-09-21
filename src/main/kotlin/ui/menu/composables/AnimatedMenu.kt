package ui.menu.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import ui.menu.composables.utils.Menu
import ui.menu.models.MenuItem

@Composable
fun AnimatedMenu(
    isVisible: Boolean,
    onSelectMenuItem : (MenuItem) -> Unit,
    onCloseMenu: () -> Unit,
) {
    val animationDurationMillis = 750
    val offsetX = -250

    AnimatedVisibility(visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { offsetX },
            animationSpec = tween(
                durationMillis = animationDurationMillis,
                easing = FastOutSlowInEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { offsetX },
            animationSpec = tween(
                durationMillis = animationDurationMillis,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        Menu(
            onSelectMenuItem = onSelectMenuItem,
            onCloseMenu = onCloseMenu
        )
    }
}

