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
import ui.common.getRandomColor
import ui.common.resources.*
import ui.tags.models.AddEditPopupButton
import ui.tags.models.Tag
import java.awt.Cursor


//TODO: De editat Confirmation Popup sa accepte ca content un composable in loc de text string
@Composable
fun AddTagPopup(
    onGetTagByLabel: (String) -> Tag?,
    onAddTag: (Tag) -> Unit,
    onUpdateTag: (Tag) -> Unit,
    onClosePopup: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTagExistsPopup by remember {
        mutableStateOf(false)
    }

    var possibleExistingTag by remember {
        mutableStateOf<Tag?>(null)
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
                            possibleExistingTag = onGetTagByLabel(tagToAdd.label)
                            if (possibleExistingTag != null) {
                                showTagExistsPopup = true
                            } else {
                                onAddTag(tagToAdd)
                                onClosePopup()
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

            if (showTagExistsPopup && possibleExistingTag != null) {
                ConfirmationPopup(
                    onConfirm = {
                        tagToAdd = tagToAdd.copy(id = possibleExistingTag!!.id)
                        onUpdateTag(tagToAdd)
                        onClosePopup()
                    },
                    onCancel = {
                        showTagExistsPopup = false
                    },
                    confirmText = "Update tag",
                    confirmColor = BUTTON_GREEN_BACKGROUND_COLOR,
                    confirmIcon = Icons.Default.Upgrade,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "The tag with the label: ${tagToAdd.label} already exists with a different color!",
                            style = TextStyle(
                                color = TEXT_COLOR,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Do you want to update the tag with the new color?",
                            style = TextStyle(
                                color = TEXT_COLOR,
                                fontSize = 14.sp,
                                letterSpacing = 1.sp,
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sell,
                                contentDescription = null,
                                tint = possibleExistingTag!!.color,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = possibleExistingTag!!.label,
                                fontSize = 20.sp,
                                color = TEXT_COLOR,
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = TEXT_COLOR,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Filled.Sell,
                                contentDescription = null,
                                tint = tagToAdd.color,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = tagToAdd.label,
                                fontSize = 20.sp,
                                color = TEXT_COLOR,
                            )
                        }
                    }
                }

            }
        }
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
