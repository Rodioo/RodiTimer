package ui.menu.composables.utils

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.menu.models.MenuItem

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MenuItem(
    item: MenuItem,
    onSelectItem: (MenuItem) -> Unit,
) {
    var isHovering by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isHovering) Color.LightGray else Color.Transparent)
            .padding(top = 12.dp, bottom = 12.dp)
            .onPointerEvent(PointerEventType.Enter) {
                isHovering = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovering = false
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onSelectItem(item)
            }

    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.name,
            fontSize = 24.sp,
            color = Color.White
        )
    }
}