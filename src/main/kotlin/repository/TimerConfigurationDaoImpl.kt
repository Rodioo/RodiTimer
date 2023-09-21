package repository

import com.tonio.database.Database
import ui.timer.models.TimerConfiguration

class TimerConfigurationDaoImpl(
    private val database: Database
) : TimerConfigurationDao {

    override fun get(): TimerConfiguration? {
        return try {
            val result = database.timerConfigurationQueries.select().executeAsOne()
            TimerConfiguration(
                mainSeconds = result.mainSeconds,
                shortBreakSeconds = result.shortBreakSeconds,
                longBreakSeconds = result.longBreakSeconds,
                rounds = result.rounds
            )
        } catch (exception: NullPointerException) {
            null
        }
    }

    override fun insert(timerConfiguration: TimerConfiguration) {
        database.timerConfigurationQueries.insertTimerConfiguration(
            mainSeconds = timerConfiguration.mainSeconds,
            shortBreakSeconds = timerConfiguration.shortBreakSeconds,
            longBreakSeconds = timerConfiguration.longBreakSeconds,
            rounds = timerConfiguration.rounds
        )
    }
}