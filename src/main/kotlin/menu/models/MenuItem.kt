package menu.models

import androidx.compose.ui.graphics.vector.ImageVector
import navigation.Screen

data class MenuItem(
    val icon: ImageVector,
    val name: String,
    val screen: Screen
)
