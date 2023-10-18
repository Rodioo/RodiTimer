package ui.home.composables.utils

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import ui.common.composables.StyledButton
import ui.common.resources.TOP_BAR_BACKGROUND_COLOR
import ui.common.resources.TOP_BAR_HEIGHT
import ui.tags.composables.utils.Tag
import ui.tags.models.Tag

enum class TagsListType{
    FAVORITE,
    ALL
}

@Composable
fun SelectTagPopup(
    tags: List<Tag>,
    onClosePopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    //TODO: add logic here to check if user has favorite tags set then by default it's set to FAVORITE
    var tagsListType by remember {
        mutableStateOf(TagsListType.ALL)
    }

    Dialog(
        onDismissRequest = onClosePopup
    ) {
        Column(
            modifier = modifier.background(TOP_BAR_BACKGROUND_COLOR)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                StyledButton(
                    onClick = {
                        tagsListType = TagsListType.FAVORITE
                    },
                    text = "Favorite",
                    leadingIcon = Icons.Default.Star,
                    backgroundColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.5f),
                    modifier = Modifier.weight(0.5f)
                )

//                Divider(
//                    thickness = 2.dp,
//                    color = Color.Black,
//                    modifier = Modifier.height(TOP_BAR_HEIGHT).width(2.dp)
//                )

                StyledButton(
                    onClick = {
                        tagsListType = TagsListType.ALL
                    },
                    text = "All tags",
                    leadingIcon = Icons.Default.Sell,
                    backgroundColor = TOP_BAR_BACKGROUND_COLOR.copy(alpha = 0.5f),
                    modifier = Modifier.weight(0.5f)
                )
            }
            Box {
                LazyColumn(
                    state = scrollState,
                    userScrollEnabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .draggable(
                            orientation = Orientation.Vertical,
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    scrollState.scrollBy(-delta)
                                }
                            }
                        )
                ) {
                    when(tagsListType) {
                        TagsListType.ALL -> {
                            items(tags) {tag ->
                                Tag(
                                    tag = tag,
                                    onEditTag = {},
                                    onUpdateFavoriteStatus = {}
                                )
                            }
                        }
                        TagsListType.FAVORITE -> {

                        }
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(
                        scrollState = scrollState
                    )
                )
            }
        }
    }
}