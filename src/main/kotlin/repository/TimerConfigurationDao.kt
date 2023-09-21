package repository

import ui.timer.models.TimerConfiguration

interface TimerConfigurationDao {

    fun get(): TimerConfiguration?

    fun insert(timerConfiguration: TimerConfiguration)
}