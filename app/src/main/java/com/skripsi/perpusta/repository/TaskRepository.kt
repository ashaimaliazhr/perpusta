package com.skripsi.perpusta.repository

import androidx.lifecycle.LiveData
import com.skripsi.perpusta.data.room.TaskDao
import com.skripsi.perpusta.data.room.TaskEntity

class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): LiveData<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    fun insertTask(task: TaskEntity) {
        taskDao.insert(task)
    }

    suspend fun deleteTask(taskEntity: TaskEntity) {
        taskDao.deleteTask(taskEntity)
    }

}