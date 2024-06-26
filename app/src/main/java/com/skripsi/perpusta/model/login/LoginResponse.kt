package com.skripsi.perpusta.model.login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    @Expose
    val message: String? = null,

    @SerializedName("token")
    @Expose
    val token: String? = null,

    @SerializedName("user")
    @Expose
    val user: User? = null
)