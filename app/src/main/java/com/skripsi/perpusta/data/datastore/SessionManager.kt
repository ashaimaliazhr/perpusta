package com.skripsi.perpusta.data.datastore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager (context: Context) {
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    companion object{
        const val KEY_USER_ID = "user_id"
        const val KEY_TOKEN = "token"
        const val KEY_FULL_NAME = "full_name"
//        const val KEY_NPM = "npm"
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

    fun getFullName(): String? {
        return sharedPreferences.getString(KEY_FULL_NAME, null)
    }

//    fun getToken(): String? {
//        return sharedPreferences.getString(KEY_TOKEN, null)
//    }

//    fun getNpm(): String? {
//        return sharedPreferences.getString(KEY_NPM, null)
//    }

//    fun getTasksForLoggedInUser(taskDao: TaskDao): LiveData<List<TaskEntity>> {
//        val loggedInUserId = getUserId()
//        if(loggedInUserId != null) {
//            return taskDao.getTasksForUser(loggedInUserId)
//        } else {
//            return MutableLiveData<List<TaskEntity>>().apply { value = emptyList() }
//        }
//    }

//    fun setLoggedInUserId(userId: String) {
//        sharedPreferences.edit {
//            putString(KEY_USER_ID, userId)
//        }
//    }

//    fun saveUserId(userId: String){
//        sharedPreferences.edit().putString("userId", userId).apply()
//    }
}