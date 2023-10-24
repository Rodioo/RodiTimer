package ui.tags.models

import androidx.compose.ui.graphics.Color

data class Tag(
    val label: String,
    val color: Color,
    val isFavorite: Boolean = false,
    val id: Long? = null
)
