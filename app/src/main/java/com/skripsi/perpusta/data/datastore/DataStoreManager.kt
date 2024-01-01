package com.skripsi.perpusta.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager (private val context: Context) {
    suspend fun setUser(username : String){
        context.userDataStore.edit {
            it[USER_KEY] = username
        }
    }

    fun getUser() : Flow<String> {
        return context.userDataStore.data.map {
            it[USER_KEY] ?: ""
        }
    }

    companion object {
        private const val USERDATA_NAME = "user_preferences"
        private const val VIEWDATA_NAME = "view_preferences"

        private val USER_KEY = stringPreferencesKey("user_key")
        private val VIEW_KEY = booleanPreferencesKey("view_key")

        private val Context.userDataStore by preferencesDataStore(
            name = USERDATA_NAME
        )

        private val Context.viewDataStore by preferencesDataStore(
            name = VIEWDATA_NAME
        )
    }


}