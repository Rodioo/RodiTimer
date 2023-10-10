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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import ui.common.composables.ConfirmationPopup
import ui.common.composables.StyledButton
import ui.common.resources.*
import ui.tags.models.AddEditPopupButton
import ui.tags.models.Tag
import java.awt.Cursor

@Composable
fun EditTagPopup(
    tag: Tag,
    onEditTag: (Tag) -> Unit,
    onDeleteTag: (Tag) -> Unit,
    onClosePopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteConfirmation by remember {
        mutableStateOf(false)
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
                EditTagPopupHeader(
                    onDelete = {
                        showDeleteConfirmation = true
                    },
                    onClose = onClosePopup
                )

                EditTagPopupBody(
                    tag = tag,
                    onEditTag = onEditTag,
                    onClosePopup = onClosePopup
                )
            }

            if (showDeleteConfirmation) {
                ConfirmationPopup(
                    onConfirm = {
                        onDeleteTag(tag)
                        onClosePopup()
                    },
                    onCancel = {
                        showDeleteConfirmation = false
                    },
                    confirmText = "Delete",
                    confirmColor = BUTTON_RED_BACKGROUND_COLOR,
                    confirmIcon = Icons.Default.Delete,
                ) {
                    Text(
                        text = "Are you sure you want to delete this tag?",
                        style = TextStyle(
                            color = TEXT_COLOR,
                            fontSize = 20.sp,
                            letterSpacing = 1.sp,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }

            }
        }
    }
}

@Composable
private fun EditTagPopupBody(
    tag: Tag,
    onEditTag: (Tag) -> Unit,
    onClosePopup: () -> Unit
) {
    var editableTag by remember {
        mutableStateOf(tag)
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
                onEditTag(editableTag)
                onClosePopup()
            },
            text = "Save tag",
            textSize = 18.sp,
            leadingIcon = Icons.Default.Edit,
            backgroundColor = BUTTON_GREEN_BACKGROUND_COLOR,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EditTagPopupHeader(
    onDelete: () -> Unit,
    onClose: () -> Unit
) {
    var hoveredOverItem by remember {
        mutableStateOf<AddEditPopupButton?>(null)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(TOP_BAR_HEIGHT)
    ) {
        Icon(
            imageVector = AddEditPopupButton.DELETE.icon,
            contentDescription = AddEditPopupButton.DELETE.description,
            tint = TEXT_COLOR,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(36.dp)
                .clickable {
                    onDelete()
                }
                .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                .onPointerEvent(PointerEventType.Enter) {
                    hoveredOverItem = AddEditPopupButton.DELETE
                }
                .onPointerEvent(PointerEventType.Exit) {
                    hoveredOverItem = null
                }
                .background(color = if (hoveredOverItem == AddEditPopupButton.DELETE) Color.LightGray else Color.Transparent)
        )

        Text(
            text = "Edit Tag",
            style = TextStyle(
                color = TEXT_COLOR,
                fontSize = 20.sp,
                letterSpacing = 2.sp,
            )
        )

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
