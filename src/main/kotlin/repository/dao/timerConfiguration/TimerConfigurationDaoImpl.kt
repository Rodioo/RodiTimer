package repository.dao.timerConfiguration

import app.cash.sqldelight.db.SqlDriver
import repository.DatabaseResponse
import repository.DatabaseResponseType
import repository.LocalDatabase
import ui.tags.models.Tag
import ui.timer.models.TimerConfiguration

class TimerConfigurationDaoImpl(
    driver: SqlDriver
) : TimerConfigurationDao {
    private val database = LocalDatabase.getInstance(driver)

    override fun get(): DatabaseResponse<TimerConfiguration?> {
        return try {
            val result = database.timerConfigurationQueries.getTimerConfiguration().executeAsOne()
            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = TimerConfiguration(
                    idTag = result.idTag,
                    mainSeconds = result.mainSeconds,
                    shortBreakSeconds = result.shortBreakSeconds,
                    longBreakSeconds = result.longBreakSeconds,
                    rounds = result.rounds
                )
            )
        } catch (exception: NullPointerException) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to get saved timer configuration.\n Please reload the screen."
            )
        }
    }

    override fun insert(timerConfiguration: TimerConfiguration): DatabaseResponse<TimerConfiguration?> {
        return try {
            database.timerConfigurationQueries.insertTimerConfiguration(
                idTag = timerConfiguration.idTag,
                mainSeconds = timerConfiguration.mainSeconds,
                shortBreakSeconds = timerConfiguration.shortBreakSeconds,
                longBreakSeconds = timerConfiguration.longBreakSeconds,
                rounds = timerConfiguration.rounds
            )

            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = timerConfiguration,
            )
        } catch (exception: Exception) {
            println(exception)
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to insert timer configuration in database.\n Please reload the screen."
            )
        }

    }
}