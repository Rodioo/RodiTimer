package ui.tags.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import ui.common.composables.ConfirmationPopup
import ui.common.composables.StyledButton
import ui.common.getRandomColor
import ui.common.resources.*
import ui.tags.models.AddEditPopupButton
import ui.tags.models.Tag
import java.awt.Cursor


//TODO: De editat Confirmation Popup sa accepte ca content un composable in loc de text string
@Composable
fun AddTagPopup(
    onCheckTagByLabel: (String) -> Tag,
    onAddTag: (Tag) -> Unit,
    onClosePopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTagExistsPopup by remember {
        mutableStateOf(false)
    }

    var tagToAdd by remember {
        mutableStateOf(
            Tag(
                label = "",
                color = getRandomColor()
            )
        )
    }

    Dialog(
        onDismissRequest = onClosePopup
    ) {
        Box(
            modifier = modifier.clip(RoundedCornerShape(8.dp)).background(color = TOP_BAR_BACKGROUND_COLOR)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AddTagPopupHeader(
                    onClose = onClosePopup
                )

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    TextField(
                        value = tagToAdd.label,
                        onValueChange = {
                            tagToAdd = tagToAdd.copy(label = it)
                        },
                        textStyle = TextStyle(
                            color = TEXT_COLOR,
                            fontSize = 18.sp
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = DIVIDER_COLOR,
                            unfocusedContainerColor = DIVIDER_COLOR
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = TEXT_COLOR,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Sell,
                                contentDescription = null,
                                tint = tagToAdd.color,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ClassicColorPicker(
                        onColorChanged = {
                            tagToAdd = tagToAdd.copy(color = it.toColor())
                        },
                        color = HsvColor.from(tagToAdd.color),
                        showAlphaBar = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StyledButton(
                        onClick = {
                            val possibleExistingTag = onCheckTagByLabel(tagToAdd.label)
                            if (tagToAdd.label == possibleExistingTag.label) {
                                showTagExistsPopup = true
                            } else {
                                onAddTag(tagToAdd)
                            }
                        },
                        text = "Add tag",
                        textSize = 18.sp,
                        leadingIcon = Icons.Default.Edit,
                        backgroundColor = BUTTON_GREEN_BACKGROUND_COLOR,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            if (showTagExistsPopup) {
                ConfirmationPopup(
                    text = "Are you sure you want to delete this tag?",
                    onConfirm = {
                        onAddTag(tagToAdd)
                    },
                    onCancel = {
                        showTagExistsPopup = false
                    },
                    confirmText = "Delete",
                    confirmColor = BUTTON_RED_BACKGROUND_COLOR,
                    confirmIcon = Icons.Default.Delete,
                )
            }
        }
    }
}

@Composable
private fun AddTagPopupBody(
    onCheckTagByLabel: (String) -> Tag,
    onAddTag: (Tag) -> Unit,
    onClosePopup: () -> Unit
) {
    var editableTag by remember {
        mutableStateOf(
            Tag(
                label = "",
                color = getRandomColor()
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        TextField(
            value = editableTag.label,
            onValueChange = {
                editableTag = editableTag.copy(label = it)
            },
            textStyle = TextStyle(
                color = TEXT_COLOR,
                fontSize = 18.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DIVIDER_COLOR,
                unfocusedContainerColor = DIVIDER_COLOR
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = TEXT_COLOR,
                    modifier = Modifier.size(28.dp)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Sell,
                    contentDescription = null,
                    tint = editableTag.color,
                    modifier = Modifier.size(28.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ClassicColorPicker(
            onColorChanged = {
                editableTag = editableTag.copy(color = it.toColor())
            },
            color = HsvColor.from(editableTag.color),
            showAlphaBar = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        StyledButton(
            onClick = {
                onCheckTagByLabel(editableTag.label)
            },
            text = "Add tag",
            textSize = 18.sp,
            leadingIcon = Icons.Default.Edit,
            backgroundColor = BUTTON_GREEN_BACKGROUND_COLOR,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AddTagPopupHeader(
    onClose: () -> Unit
) {
    var hoveredOverItem by remember {
        mutableStateOf<AddEditPopupButton?>(null)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(TOP_BAR_HEIGHT)
    ) {

        Text(
            text = "Add Tag",
            style = TextStyle(
                color = TEXT_COLOR,
                fontSize = 20.sp,
                letterSpacing = 2.sp,
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = AddEditPopupButton.CLOSE.icon,
            contentDescription = AddEditPopupButton.CLOSE.description,
            tint = TEXT_COLOR,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(36.dp)
                .clickable {
                    onClose()
                }
                .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                .onPointerEvent(PointerEventType.Enter) {
                    hoveredOverItem = AddEditPopupButton.CLOSE
                }
                .onPointerEvent(PointerEventType.Exit) {
                    hoveredOverItem = null
                }
                .background(color = if (hoveredOverItem == AddEditPopupButton.CLOSE) Color.LightGray else Color.Transparent)
        )
    }
}
