package com.skripsi.perpusta.repository

import com.skripsi.perpusta.data.network.ApiService
import com.skripsi.perpusta.model.login.LoginResponse
import com.skripsi.perpusta.data.result.Result

class AuthRepository (
    private val api: ApiService
    ) : BaseRepository(){

        suspend fun login(
            npm: String,
            password: String
        ): Result<LoginResponse> {
            return safeApiCall {
                api.loginUser(npm, password)
            }
        }
    }