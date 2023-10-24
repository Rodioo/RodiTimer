package repository.dao.tag

import repository.DatabaseResponse
import ui.tags.models.Tag
import ui.timer.models.TimerConfiguration

interface TagDao {

    fun getByLabel(label: String): DatabaseResponse<Tag?>

    fun getById(id: Long?): DatabaseResponse<Tag?>

    fun getAll(): DatabaseResponse<List<Tag>?>

    fun insert(tag: Tag): DatabaseResponse<Tag?>

    fun update(newTag: Tag): DatabaseResponse<Tag?>

    fun delete(id: Long?): DatabaseResponse<Unit?>
}