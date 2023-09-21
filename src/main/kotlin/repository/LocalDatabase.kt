package repository

import app.cash.sqldelight.db.SqlDriver
import com.tonio.database.Database

abstract class LocalDatabase : Database {
    companion object {

        @Volatile
        private var INSTANCE: Database? = null

        fun getInstance(driver: SqlDriver): Database {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Database(driver)

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}