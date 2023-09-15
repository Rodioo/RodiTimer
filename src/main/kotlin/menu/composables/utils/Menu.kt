package menu.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.resources.TOP_BAR_HEIGHT
import common.resources.WINDOW_SIZE
import menu.models.MenuItem
import navigation.Screen

@Composable
fun Menu(
    onSelectMenuItem : (MenuItem) -> Unit,
    onCloseMenu: () -> Unit
) {
    val menuItems = listOf(
        MenuItem(
            Icons.Default.Home,
            "Home",
            Screen.Home,
        ),
        MenuItem(
            Icons.Default.Timer,
            "Timer",
            Screen.Timer,
        ),
        MenuItem(
            Icons.Default.NotificationsActive,
            "Alarm",
            Screen.Alarm,
        ),
        MenuItem(
            Icons.Default.Sell,
            "Tags",
            Screen.Tags,
        ),
        MenuItem(
            Icons.Default.Settings,
            "Settings",
            Screen.Settings,
        ),
        MenuItem(
            Icons.Default.Help,
            "About",
            Screen.About,
        ),
    )

    Column(
        modifier = Modifier
            .height(WINDOW_SIZE.height - TOP_BAR_HEIGHT)
            .width(180.dp)
            .clip(RoundedCornerShape(bottomEnd = 24.dp))
            .background(
                brush = Brush.verticalGradient(
                    0.12f to Color(0xFF181A1B),
                    1f to Color(0xFF3F4142)
                )
            )
            .padding(4.dp)
    ) {
        MenuHeader(onCloseMenu)

        Spacer(modifier = Modifier.height(32.dp))

        menuItems.map {item ->
            MenuItem(
                item,
                onSelectItem = {
                    onSelectMenuItem(it)
                    onCloseMenu()
                }
            )
        }
    }
}