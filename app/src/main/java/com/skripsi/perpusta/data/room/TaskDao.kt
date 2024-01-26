package com.skripsi.perpusta.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TaskEntity>>

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}