package repository

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.tonio.database.Database

interface Driver {
    fun createDriver(): SqlDriver
}

class DriverFactory : Driver {
    override fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
        Database.Schema.create(driver)
        return driver
    }
}