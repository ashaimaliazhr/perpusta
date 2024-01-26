package com.skripsi.perpusta.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Migration2To1 : Migration(2, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // SQL statement to remove the userId column
            database.execSQL("CREATE TABLE tasks_temp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT NOT NULL, hour TEXT NOT NULL, date TEXT NOT NULL, reminderTime INTEGER NOT NULL)")
            database.execSQL("INSERT INTO tasks_temp (id, title, hour, date, reminderTime) SELECT id, title, hour, date, reminderTime FROM tasks")
            database.execSQL("DROP TABLE tasks")
            database.execSQL("ALTER TABLE tasks_temp RENAME TO tasks")
        }
    }

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(Migration2To1()) // Add migrations here
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}