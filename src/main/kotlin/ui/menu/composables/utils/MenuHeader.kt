package ui.menu.composables.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MenuHeader(
    onCloseMenu: () -> Unit
) {
    var isHoveringExitButton by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onCloseMenu,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.TopEnd)
                .background(color = if (isHoveringExitButton) Color.LightGray else Color.Transparent)
                .onPointerEvent(PointerEventType.Enter) {
                    isHoveringExitButton = true
                }
                .onPointerEvent(PointerEventType.Exit) {
                    isHoveringExitButton = false
                }
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Exit menu button",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Image(
            painter = painterResource("drawable/dummy_logo.png"),
            contentDescription = "Logo image",
            modifier = Modifier
                .size(112.dp)
                .align(Alignment.CenterStart)
                .padding(start = 32.dp)
        )

        Text(
            text = "RodiTimer",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp)
        )
    }
}