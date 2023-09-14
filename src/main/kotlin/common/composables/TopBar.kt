package common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowScope
import common.resources.TOP_BAR_HEIGHT

enum class TopBarButton(
    val icon: ImageVector,
    val description: String
) {
    MENU(Icons.Default.Menu, "Menu icon"),
    MINIMIZE(Icons.Default.HorizontalRule,"Minimize icon"),
    EXIT(Icons.Default.Close,"Exit icon")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FrameWindowScope.TopBar(
    resetWindowSize: () -> Unit,
    onClickMenu: () -> Unit,
    onMinimize: () -> Unit,
    onClose: () -> Unit
) {
    var hoveredOverItem by remember {
        mutableStateOf<TopBarButton?>(null)
    }

    WindowDraggableArea(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .onPointerEvent(PointerEventType.Move) {
                resetWindowSize()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF181A1B))
        ) {
            IconButton(
                onClick = onClickMenu,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .onPointerEvent(PointerEventType.Enter) {
                        hoveredOverItem = TopBarButton.MENU
                    }
                    .onPointerEvent(PointerEventType.Exit) {
                        hoveredOverItem = null
                    }
                    .background(color = if (hoveredOverItem == TopBarButton.MENU) Color.LightGray else Color.Transparent)
            ) {
                Icon(
                    imageVector = TopBarButton.MENU.icon,
                    contentDescription = TopBarButton.MENU.description,
                    tint = Color.White
                )
            }

            Spacer(Modifier.width(212.dp))

            Text(
                "RodiTimer",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Spacer(Modifier.width(180.dp))

            IconButton(
                onClick = onMinimize,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .onPointerEvent(PointerEventType.Enter) {
                        hoveredOverItem = TopBarButton.MINIMIZE
                    }
                    .onPointerEvent(PointerEventType.Exit) {
                        hoveredOverItem = null
                    }
                    .background(color = if (hoveredOverItem == TopBarButton.MINIMIZE) Color.LightGray else Color.Transparent)
            ) {
                Icon(
                    imageVector = TopBarButton.MINIMIZE.icon,
                    contentDescription = TopBarButton.MINIMIZE.description,
                    tint = Color.White
                )
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .onPointerEvent(PointerEventType.Enter) {
                        hoveredOverItem = TopBarButton.EXIT
                    }
                    .onPointerEvent(PointerEventType.Exit) {
                        hoveredOverItem = null
                    }
                    .background(color = if (hoveredOverItem == TopBarButton.EXIT) Color.Red else Color.Transparent)
            ) {
                Icon(
                    imageVector = TopBarButton.EXIT.icon,
                    contentDescription = TopBarButton.EXIT.description,
                    tint = Color.White
                )
            }
        }
    }
}