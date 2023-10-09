package ui.tags.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.vector.ImageVector

enum class AddEditPopupButton(
    val icon: ImageVector,
    val description: String
) {
    DELETE(Icons.Default.Delete, "Delete icon"),
    CLOSE(Icons.Default.Close, "Close icon")
}