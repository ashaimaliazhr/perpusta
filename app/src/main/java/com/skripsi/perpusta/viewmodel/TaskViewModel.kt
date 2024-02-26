package com.skripsi.perpusta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.data.room.AppDatabase
import com.skripsi.perpusta.data.room.TaskEntity
import com.skripsi.perpusta.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: TaskRepository
    val allTasks: LiveData<List<TaskEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        val taskDao = database.taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.getAllTasks()
    }

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun getTasksByUserId(userId: String): LiveData<List<TaskEntity>> {
        return repository.getAllTasksForUser(userId)
    }

    fun deleteTask(taskEntity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(taskEntity)
        }
    }

}