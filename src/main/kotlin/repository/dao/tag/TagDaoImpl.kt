package repository.dao.tag

import app.cash.sqldelight.db.SqlDriver
import repository.DatabaseResponse
import repository.DatabaseResponseType
import repository.LocalDatabase
import ui.tags.models.Tag

class TagDaoImpl(
    driver: SqlDriver
) : TagDao {
    private val database = LocalDatabase.getInstance(driver)

    override fun getByLabel(label: String): DatabaseResponse<Tag?> {
        return try {
            val result = database.tagsQueries.getTagByLabel(label).executeAsOne()
            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = Tag(
                    id = result.id,
                    label = result.label,
                    color = result.color,
                    isFavorite = result.isFavorite
                )
            )
        } catch (exception: NullPointerException) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to get tag with label: $label.\n Please reload the screen."
            )
        }
    }

    override fun getById(id: Long?): DatabaseResponse<Tag?> {
        return try {
            if (id == null) {
                throw NullPointerException()
            }

            val result = database.tagsQueries.getTagById(id).executeAsOne()
            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = Tag(
                    id = result.id,
                    label = result.label,
                    color = result.color,
                    isFavorite = result.isFavorite
                )
            )
        } catch (exception: NullPointerException) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to get tag with id: $id.\n Please reload the screen."
            )
        }
    }

    override fun getAll(): DatabaseResponse<List<Tag>?> {
        return try {
            val result = database.tagsQueries.getAllTags().executeAsList()
            val tagsList = mutableListOf<Tag>()
            result.forEach {
                tagsList.add(
                    Tag(
                        id = it.id,
                        label = it.label,
                        color = it.color,
                        isFavorite = it.isFavorite
                    )
                )
            }

            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = tagsList
            )
        } catch (exception: NullPointerException) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to get saved tags.\n Please reload the screen."
            )
        }
    }

    override fun insert(tag: Tag): DatabaseResponse<Tag?> {
        val possibleExistingTag = getByLabel(tag.label).data
        if (possibleExistingTag == null) {
            return try {
                database.tagsQueries.insertTag(
                    id = null,
                    label = tag.label,
                    color = tag.color,
                    isFavorite = tag.isFavorite
                )

                DatabaseResponse(
                    type = DatabaseResponseType.SUCCESS,
                    data = tag,
                )
            } catch (exception: Exception) {
                DatabaseResponse(
                    type = DatabaseResponseType.FAILED,
                    data = null,
                    additionalMessage = "Failed to insert tag in database.\n Please reload the screen."
                )
            }
        } else if (possibleExistingTag.label == tag.label) {
            return DatabaseResponse(
                type = DatabaseResponseType.NEEDS_CONFIRMATION,
                data = possibleExistingTag,
                additionalMessage = ""
            )
        } else {
            return DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to insert tag in database.\n Please reload the screen."
            )
        }
    }

    override fun update(newTag: Tag): DatabaseResponse<Tag?> {
        return try {
            database.tagsQueries.updateTag(
                id = newTag.id ?: throw NullPointerException(),
                label = newTag.label,
                color = newTag.color,
                isFavorite = newTag.isFavorite
            )

            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = newTag,
            )
        } catch (exception: Exception) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to update tag with id: ${newTag.id}.\n Please reload the screen."
            )
        }
    }

    override fun delete(id: Long?): DatabaseResponse<Unit?> {
        return try {
            database.tagsQueries.deleteTag(
                id = id ?: throw NullPointerException()
            )

            DatabaseResponse(
                type = DatabaseResponseType.SUCCESS,
                data = Unit,
            )
        } catch (exception: Exception) {
            DatabaseResponse(
                type = DatabaseResponseType.FAILED,
                data = null,
                additionalMessage = "Failed to delete tag with id: $id.\n Please reload the screen."
            )
        }
    }
}