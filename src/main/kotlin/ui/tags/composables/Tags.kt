package ui.tags.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.common.composables.NoRippleTheme
import ui.common.debounce
import ui.common.resources.*
import ui.tags.composables.utils.AddTagPopup
import ui.tags.composables.utils.EditTagPopup
import ui.tags.composables.utils.Tag
import ui.tags.models.Tag
import ui.tags.viewmodels.TagsViewModel
import java.awt.Cursor

//TODO: fix bug on clicking outside the left menu when it's open
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

    var displayAddPopup by remember {
        mutableStateOf(false)
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
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = {
                    searchText = it
                    val onDebouncedSearchChange = debounce(
                        coroutineScope = coroutineScope,
                        onChange = viewModel::filterTags
                    )
                    onDebouncedSearchChange(searchText)
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
                        modifier = Modifier
                            .clickable { viewModel.filterTags(searchText) }
                            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
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
                        unfocusedTextColor = TEXT_COLOR,
                        disabledTextColor = TEXT_COLOR
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = TAGS_MARGIN)
            ) {}

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(0.85f)
                ) {
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
                        items(tags) {tag ->
                            Tag(
                                tag = tag,
                                onEditTag = { editableTag ->
                                    editTag = editableTag
                                    displayEditPopup = true
                                },
                                onUpdateFavoriteStatus = {
                                    val tag = it.copy(isFavorite = !it.isFavorite)
                                    viewModel.updateTag(tag)
                                }
                            )
                        }
                    }

                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = scrollState
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme()) {
                    FloatingActionButton(
                        onClick = { displayAddPopup = true },
                        shape = CircleShape,
                        containerColor = BUTTON_DEFAULT_BACKGROUND_COLOR,
                        contentColor = TEXT_COLOR,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add tag",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (displayAddPopup) {
            AddTagPopup(
                onGetTagByLabel = {
                    viewModel.getTagByLabel(it)
                },
                onAddTag = {
                    viewModel.insertTag(it)
                },
                onUpdateTag = {
                    viewModel.updateTag(it)
                },
                onClosePopup = {
                    displayAddPopup = false
                },
                modifier = Modifier.align(Alignment.Center).width(400.dp).height(300.dp)
            )
        }

        if (displayEditPopup && editTag != null) {
            EditTagPopup(
                tag = editTag!!,
                onEditTag = {
                  viewModel.updateTag(it)
                },
                onDeleteTag = {
                    viewModel.deleteTag(it.id)
                },
                onClosePopup = {
                    displayEditPopup = false
                },
                modifier = Modifier.align(Alignment.Center).width(400.dp).height(300.dp)
            )
        }
    }
}
