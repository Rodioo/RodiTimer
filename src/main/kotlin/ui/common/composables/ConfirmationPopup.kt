package ui.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.common.resources.BUTTON_DEFAULT_BACKGROUND_COLOR
import ui.common.resources.BUTTON_GREEN_BACKGROUND_COLOR
import ui.common.resources.TEXT_COLOR
import ui.common.resources.TOP_BAR_BACKGROUND_COLOR

@Composable
fun ConfirmationPopup(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    confirmText: String,
    cancelText: String = "Cancel",
    confirmColor: Color = BUTTON_GREEN_BACKGROUND_COLOR,
    cancelColor: Color = BUTTON_DEFAULT_BACKGROUND_COLOR,
    confirmIcon: ImageVector = Icons.Default.Done,
    cancelIcon: ImageVector = Icons.Default.Cancel,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onCancel
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(RoundedCornerShape(24.dp))
                .background(color = TOP_BAR_BACKGROUND_COLOR)
                .padding(16.dp)
        ) {
            content()

            Spacer(modifier = Modifier.height(64.dp))

            Row {
                StyledButton(
                    onClick = onCancel,
                    text = cancelText,
                    leadingIcon = cancelIcon,
                    backgroundColor = cancelColor
                )

                Spacer(modifier = Modifier.width(32.dp))

                StyledButton(
                    onClick = onConfirm,
                    text = confirmText,
                    leadingIcon = confirmIcon,
                    backgroundColor = confirmColor,
                )
            }
        }
    }
}