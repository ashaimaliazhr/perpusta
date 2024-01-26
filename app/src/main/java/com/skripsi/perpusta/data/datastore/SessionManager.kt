package com.skripsi.perpusta.data.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.skripsi.perpusta.data.room.TaskDao
import com.skripsi.perpusta.data.room.TaskEntity

class SessionManager (context: Context) {
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    companion object{
        const val KEY_USER_ID = "user_id"
        const val KEY_TOKEN = "token"
        const val KEY_FULL_NAME = "full_name"
        const val KEY_NPM = "npm"
    }

    fun saveUserData(userId: String, token: String, fullName: String) {
        sharedPreferences.edit{
            putString(KEY_USER_ID, userId)
            putString(KEY_TOKEN, token)
            putString(KEY_FULL_NAME, fullName)
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun getFullName(): String? {
        return sharedPreferences.getString(KEY_FULL_NAME, null)
    }

    fun getNpm(): String? {
        return sharedPreferences.getString(KEY_NPM, null)
    }

    fun getTasksForLoggedInUser(taskDao: TaskDao): LiveData<List<TaskEntity>> {
        val loggedInUserId = getUserId()
        return taskDao.getAllTasks()
    }

    fun setLoggedInUserId(userId: String) {
        sharedPreferences.edit {
            putString(KEY_USER_ID, userId)
        }
    }
}