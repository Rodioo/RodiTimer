package common.composables

import androidx.compose.foundation.background
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.Cursor

@Composable
fun ResetToDefaultButton(
    onClick : () -> Unit,
    backgroundColor: Color = Color(0xFFE63B2E),
    contentColor: Color = Color.White.copy(0.9f),
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
            Icon(
                imageVector = Icons.Default.RestartAlt,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp)
            )

            Text(
                text = "Reset to default",
                color = contentColor,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = 24.dp)
            )
        }
    }
}