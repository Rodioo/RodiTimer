package ui.tags.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ui.common.resources.BACKGROUND_COLOR
import ui.common.resources.DIVIDER_COLOR
import ui.common.resources.TEXT_COLOR
import ui.common.resources.TOP_BAR_BACKGROUND_COLOR
import ui.tags.composables.utils.Tag
import ui.tags.models.Tag
import ui.tags.viewmodels.TagsViewModel
import java.awt.Cursor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tags(
    viewModel: TagsViewModel = remember { TagsViewModel() }
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var searchText by remember {
        mutableStateOf("")
    }

    var displayEditPopup by remember {
        mutableStateOf(false)
    }

    var editTag by remember {
        mutableStateOf<Tag?>(null)
    }

    val tags by viewModel.tags.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, bottom = 24.dp)
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = {
                    searchText = it
                    viewModel.filterTags(searchText)
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = {
                    Text(
                        text = "Search for a tag...",
                        color = TEXT_COLOR,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close search icon",
                            modifier = Modifier
                                .clickable {
                                    searchText = ""
                                    viewModel.filterTags(searchText)
                                }
                                .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                        )
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = DIVIDER_COLOR,
                    dividerColor = TOP_BAR_BACKGROUND_COLOR,
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = TEXT_COLOR,
                        unfocusedTextColor = TEXT_COLOR
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp)
            ) {}

            LazyColumn(
                state = scrollState,
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
                items(tags) {
                    Tag(
                        tag = it,
                        onEditTag = {
                            editTag = it
                            displayEditPopup = true
                        }
                    )
                }
            }
        }

        if (displayEditPopup && editTag != null) {
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(editTag!!.label)
            }
        }
    }
}