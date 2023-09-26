package ui.tags.viewmodels

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import ui.tags.models.Tag

class TagsViewModel {
    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val dbScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _error = MutableStateFlow<String?>(null)
        val error = _error.asStateFlow()

    private lateinit var _allTags: List<Tag>
    private val _tags = MutableStateFlow<List<Tag>>(listOf())
        val tags = _tags.asStateFlow()

    init {
        _allTags = getTags()
        _tags.value = _allTags
    }

    private fun getTags(): List<Tag> {
        return listOf(
            Tag(
                id = 1,
                color = Color.Red,
                label = "Study"
            ),
            Tag(
                id = 2,
                color = Color.Green,
                label = "Entertainment"
            ),
            Tag(
                id = 3,
                color = Color.Yellow,
                label = "Read"
            ),
            Tag(
                id = 4,
                color = Color.Blue,
                label = "Work"
            ),
            Tag(
                id = 5,
                color = Color.Magenta,
                label = "Social"
            ),
            Tag(
                id = 6,
                color = Color.White,
                label = "Project"
            ),
            Tag(
                id = 7,
                color = Color.Yellow,
                label = "Public Event"
            ),Tag(
                id = 8,
                color = Color.Green,
                label = "Nap"
            ),

            Tag(
                id = 9,
                color = Color.Cyan,
                label = "Other"
            ),
        )
    }

    fun filterTags(query: String) {
        _tags.value = _allTags.filter {
            it.label.contains(query, ignoreCase = true)
        }
    }
}