package com.skripsi.perpusta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.repository.AuthRepository
import com.skripsi.perpusta.model.login.LoginResponse
import kotlinx.coroutines.launch
import com.skripsi.perpusta.data.result.Result

class AuthViewModel (
    private val authRepository: AuthRepository
        ) : ViewModel() {

            private val _loginResult = MutableLiveData<Result<LoginResponse>>()
            val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

            fun loginUser(npm: String, password: String){
                viewModelScope.launch {
                    _loginResult.value = authRepository.login(npm, password)
                }
            }

}