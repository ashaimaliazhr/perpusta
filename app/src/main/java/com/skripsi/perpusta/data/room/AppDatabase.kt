package com.skripsi.perpusta.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TaskEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Migration1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE tasks ADD COLUMN userId TEXT NOT NULL DEFAULT ''")

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
                    .addMigrations(Migration1To2()) // Add migrations here
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}