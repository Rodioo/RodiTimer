package ui.home.composables.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleOutline
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import ui.common.resources.CIRCLE_BUTTON_SIZE
import ui.common.resources.TEXT_COLOR
import java.awt.Cursor

@Composable
fun PlayPauseButton(
    isRunning: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = if (isRunning) Icons.Default.PauseCircleOutline else Icons.Default.PlayCircleOutline,
            contentDescription = if (isRunning) "Pause Button" else "Play Button",
            tint = TEXT_COLOR,
            modifier = Modifier
                .size(CIRCLE_BUTTON_SIZE)
                .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
        )
    }
}