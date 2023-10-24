package ui.home.viewmodels

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import repository.DriverFactory
import repository.dao.tag.TagDao
import repository.dao.tag.TagDaoImpl
import repository.dao.timerConfiguration.TimerConfigurationDao
import repository.dao.timerConfiguration.TimerConfigurationDaoImpl
import ui.tags.models.Tag
import ui.timer.models.TimerConfiguration

class HomeViewModel {
    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val dbScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val timerConfigurationDao: TimerConfigurationDao
    private val tagDao: TagDao

    private val _error = MutableStateFlow<String?>(null)
        val error = _error.asStateFlow()

    private val _tags = MutableStateFlow<List<Tag>>(listOf())
        val tags = _tags.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
        val isTimerRunning = _isTimerRunning.asStateFlow()

    private val _timerConfiguration = MutableStateFlow(TimerConfiguration())
        val timerConfiguration = _timerConfiguration.asStateFlow()

    private val _time = MutableStateFlow(_timerConfiguration.value.mainSeconds)
        val time = _time.asStateFlow()

    init {
        val driver = DriverFactory().createSQLiteDriver()
        timerConfigurationDao =  TimerConfigurationDaoImpl(driver)
        tagDao =  TagDaoImpl(driver)

        getTimerConfiguration()
        getTags()
    }

    private fun getTags() {
        dbScope.launch {
            val response = tagDao.getAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _tags.value = response.data ?: listOf()
                } else {
                    _tags.value = listOf()
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    private fun getTimerConfiguration() {
        dbScope.launch {
            val response = timerConfigurationDao.get()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _timerConfiguration.value = response.data ?: _timerConfiguration.value
                    _time.value = _timerConfiguration.value.mainSeconds
                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun getTagInfo(): Tag? = runBlocking {
        var tag: Tag? = null

        val response = tagDao.getById(_timerConfiguration.value.idTag)
        if (response.isSuccessful) {
            tag = response.data
        } else {
            _error.value = response.additionalMessage
        }

        return@runBlocking tag
    }

    fun updateTimerConfigurationTag(newTag: Tag) {
        _timerConfiguration.value = _timerConfiguration.value.copy(idTag = newTag.id)
        dbScope.launch {
            val response = timerConfigurationDao.insert(_timerConfiguration.value)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _timerConfiguration.value = response.data ?: _timerConfiguration.value
                } else {
                    _error.value = response.additionalMessage
                    println(_error.value)
                }
            }
        }
    }

    private fun playTimer() {
        _isTimerRunning.value = true

        mainScope.launch {
            while (_time.value > 0 && _isTimerRunning.value) {
                delay(1_000L)
                _time.value--
            }
        }
    }

    private fun pauseTimer() {
        _isTimerRunning.value = false
    }

    fun handlePlayPause() {
        if (_isTimerRunning.value) {
            pauseTimer()
        } else {
            playTimer()
        }
    }
}