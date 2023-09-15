package home.viewmodels

import androidx.compose.runtime.*
import kotlinx.coroutines.*

//Clean this
class HomeViewModel {
    var isTimerRunning by mutableStateOf(false)
    var startTime by mutableStateOf(10L)

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private fun playTimer() {
        isTimerRunning = true

        coroutineScope.launch {
            while (startTime > 0 && isTimerRunning) {
                delay(1_000L)
                startTime--
            }
        }
    }

    private fun pauseTimer() {
        isTimerRunning = false
    }

    fun handlePlayPause() {
        if (isTimerRunning) {
            pauseTimer()
        } else {
            playTimer()
        }
    }
}