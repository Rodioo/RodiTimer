package repository

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.tonio.database.Database
import com.tonio.database.Tags
import com.tonio.database.TimerConfigurations
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import repository.dao.timerConfiguration.TimerConfigurationDaoImpl
import ui.common.toColor
import ui.tags.models.Tag

val tagsColorAdapter = object : ColumnAdapter<Color, String> {
    override fun decode(databaseValue: String): Color {
        return databaseValue.toColor() ?: Color.Transparent
    }

    override fun encode(value: Color): String {
        return value.toString()
    }
}

val timerConfigurationTagAdapter = object : ColumnAdapter<Tag, String> {
    override fun decode(databaseValue: String): Tag {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: Tag): String {
        return Json.encodeToString(value)
    }
}

abstract class LocalDatabase : Database {

    companion object {

        @Volatile
        private var INSTANCE: Database? = null

        fun getInstance(driver: SqlDriver): Database {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Database(
                        driver = driver,
                        TagsAdapter = Tags.Adapter(
                            colorAdapter = tagsColorAdapter
                        ),
                        TimerConfigurationsAdapter = TimerConfigurations.Adapter(
                            tagAdapter = timerConfigurationTagAdapter
                        )
                    )

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}