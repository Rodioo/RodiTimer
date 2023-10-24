package repository

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.tonio.database.Database
import com.tonio.database.Tags
import ui.common.toColor

val tagsColorAdapter = object : ColumnAdapter<Color, String> {
    override fun decode(databaseValue: String): Color {
        return databaseValue.toColor() ?: Color.Transparent
    }

    override fun encode(value: Color): String {
        return value.toString()
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
                        )
                    )

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}