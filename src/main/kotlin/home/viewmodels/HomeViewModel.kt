package home.viewmodels

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {
    private val _isTimerRunning = MutableStateFlow(false)
        val isTimerRunning = _isTimerRunning.asStateFlow()

    private val _maxTime = MutableStateFlow(60L)
        val maxTime = _maxTime.asStateFlow()

    private val _startTime = MutableStateFlow(_maxTime.value)
        val startTime = _startTime.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun playTimer() {
        _isTimerRunning.value = true

        coroutineScope.launch {
            while (_startTime.value > 0 && _isTimerRunning.value) {
                delay(1_000L)
                _startTime.value--
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