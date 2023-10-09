package ui.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.common.resources.*
import java.awt.Cursor

@Composable
fun StyledButton(
    onClick : () -> Unit,
    text: String,
    textSize: TextUnit = 18.sp,
    leadingIcon: ImageVector? = null,
    leadingIconSize: Dp = 20.dp,
    backgroundColor: Color = BUTTON_DEFAULT_BACKGROUND_COLOR,
    contentColor: Color = TEXT_COLOR,
    modifier : Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
    ) {
        Box {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(leadingIconSize)
                )
            }

            Text(
                text = text,
                style = TextStyle(
                    color = contentColor,
                    fontSize = textSize,
                ),
                textAlign = TextAlign.Center,
                modifier = if (leadingIcon != null) Modifier.padding(start = 24.dp) else Modifier
            )
        }
    }
}