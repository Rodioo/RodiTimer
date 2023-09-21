package ui.menu.models

import androidx.compose.ui.graphics.vector.ImageVector
import ui.navigation.Screen

data class MenuItem(
    val icon: ImageVector,
    val name: String,
    val screen: Screen
)
