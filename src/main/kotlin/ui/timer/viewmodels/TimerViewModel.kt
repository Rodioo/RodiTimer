package ui.timer.viewmodels

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import repository.DriverFactory
import repository.dao.timerConfiguration.TimerConfigurationDao
import repository.dao.timerConfiguration.TimerConfigurationDaoImpl
import ui.timer.models.TimerConfiguration
import ui.timer.models.TimerConfigurationType
import ui.timer.viewmodels.utils.onLongBreakSliderChange
import ui.timer.viewmodels.utils.onMainSliderChange
import ui.timer.viewmodels.utils.onNumberOfRoundsSliderChange
import ui.timer.viewmodels.utils.onShortBreakSliderChange

class TimerViewModel {
    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val dbScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val timerConfigurationDao: TimerConfigurationDao

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _timerConfiguration = MutableStateFlow(TimerConfiguration())
        val timerConfiguration = _timerConfiguration.asStateFlow()

    init {
        val driver = DriverFactory().createSQLiteDriver()
        timerConfigurationDao =  TimerConfigurationDaoImpl(driver)

        getTimerConfiguration()
    }

    private fun getTimerConfiguration() {
        dbScope.launch {
            val response = timerConfigurationDao.get()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _timerConfiguration.value = response.data ?: _timerConfiguration.value
                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun saveCurrentTimerConfiguration() {
        dbScope.launch {
            val response = timerConfigurationDao.insert(_timerConfiguration.value)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _timerConfiguration.value = response.data ?: _timerConfiguration.value
                } else {
                    _error.value = response.additionalMessage
                }
            }
        }
    }

    fun updateTimerConfiguration(timerConfigurationType: TimerConfigurationType, newSliderValue: Float) {
        when (timerConfigurationType) {
            TimerConfigurationType.MAIN -> _timerConfiguration.onMainSliderChange(newSliderValue)
            TimerConfigurationType.SHORT_BREAK -> _timerConfiguration.onShortBreakSliderChange(newSliderValue)
            TimerConfigurationType.LONG_BREAK -> _timerConfiguration.onLongBreakSliderChange(newSliderValue)
            TimerConfigurationType.ROUNDS -> _timerConfiguration.onNumberOfRoundsSliderChange(newSliderValue)
        }
    }

    fun resetTimerConfiguration() {
        _timerConfiguration.value = TimerConfiguration()
        saveCurrentTimerConfiguration()
    }
}