package com.skripsi.perpusta.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.repository.AuthRepository
import com.skripsi.perpusta.repository.BaseRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val repository: BaseRepository,
        ) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository as AuthRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}

