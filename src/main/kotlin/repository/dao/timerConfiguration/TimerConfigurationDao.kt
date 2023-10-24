package repository.dao.timerConfiguration

import repository.DatabaseResponse
import ui.tags.models.Tag
import ui.timer.models.TimerConfiguration

interface TimerConfigurationDao {

    fun get(): DatabaseResponse<TimerConfiguration?>

    fun insert(timerConfiguration: TimerConfiguration): DatabaseResponse<TimerConfiguration?>
}