package ui.home.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.common.resources.DIVIDER_COLOR
import ui.common.resources.TEXT_COLOR
import ui.common.resources.TOP_BAR_BACKGROUND_COLOR
import ui.tags.models.Tag

@Composable
fun SelectTagPopup(
    tags: List<Tag>,
    onClosePopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onClosePopup
    ) {
        Column(
            modifier = modifier.background(TOP_BAR_BACKGROUND_COLOR)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Favorite",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = TEXT_COLOR,
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    )
                )

                Divider(
                    thickness = 2.dp,
                    color = DIVIDER_COLOR
                )

                Text(
                    text = "All",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = TEXT_COLOR,
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    )
                )
            }
        }
    }
}