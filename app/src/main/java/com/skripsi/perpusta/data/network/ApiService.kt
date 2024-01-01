package com.skripsi.perpusta.data.network

import com.skripsi.perpusta.model.login.LoginResponse
import com.skripsi.perpusta.model.users.UserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("npm") npm: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("user/singleUser")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Query("npm") npm: String
    ): Response<UserResponse>

}