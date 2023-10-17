package ui.tags.viewmodels

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import repository.DriverFactory
import repository.dao.tag.TagDao
import repository.dao.tag.TagDaoImpl
import ui.tags.models.Tag

class TagsViewModel {
    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val dbScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val tagDao: TagDao

    private val _error = MutableStateFlow<String?>(null)
        val error = _error.asStateFlow()

    private lateinit var _allTags: List<Tag>
    private val _tags = MutableStateFlow<List<Tag>>(listOf())
        val tags = _tags.asStateFlow()

    init {
        val driver = DriverFactory().createSQLiteDriver()
        tagDao =  TagDaoImpl(driver)

        getTags()
    }

    private fun getTags() {
        dbScope.launch {
            val response = tagDao.getAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _allTags = response.data ?: listOf()
                    _tags.value = _allTags
                } else {
                    _allTags = listOf()
                    _tags.value = _allTags

                    _error.value = response.additionalMessage
                }
            }
        }
    }

    //TODO: research if its better to use withContext/runBlocking here instead of dbScope.launch
    fun getTagByLabel(label: String): Tag? = runBlocking {
        var tag: Tag? = null

        val response = tagDao.getByLabel(label)
        if (response.isSuccessful) {
            tag = response.data
        } else {
            _error.value = response.additionalMessage
        }

        return@runBlocking tag
    }

    fun insertTag(tag: Tag) {
        dbScope.launch {
            val response = tagDao.insert(tag)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getTags()
                } else if (response.needsConfirmation) {

                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun updateTag(newTag: Tag) {
        dbScope.launch {
            val response = tagDao.update(newTag)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getTags()
                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun deleteTag(id: Long?) {
        dbScope.launch {
            val response = tagDao.delete(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getTags()
                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun filterTags(query: String) {
        _tags.value = _allTags.filter {
            it.label.contains(query, ignoreCase = true)
        }
    }
}