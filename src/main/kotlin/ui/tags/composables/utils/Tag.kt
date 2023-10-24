package ui.tags.composables.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.common.resources.DIVIDER_COLOR
import ui.common.resources.TAGS_MARGIN
import ui.common.resources.TEXT_COLOR
import ui.tags.models.Tag
import java.awt.Cursor

@Composable
fun Tag(
    tag: Tag,
    onClickTag: (Tag) -> Unit,
    enabledFavoriteIcon: Boolean = true,
    onUpdateFavoriteStatus: (Tag) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClickTag(tag) }
            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = TAGS_MARGIN)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Sell,
                    contentDescription = null,
                    tint = tag.color,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = tag.label,
                    fontSize = 20.sp,
                    color = TEXT_COLOR,
                )
            }

            IconButton(
                enabled = enabledFavoriteIcon,
                onClick = {
                    onUpdateFavoriteStatus(tag)
                },
            ) {
                if (tag.isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Tag is favorite icon",
                        tint = TEXT_COLOR,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Tag is not favorite icon",
                        tint = TEXT_COLOR,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = DIVIDER_COLOR,
            modifier = Modifier.fillMaxWidth()
        )
    }
}