package com.skripsi.perpusta.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("npm")
    @Expose
    var npm : String? = null,

    @SerializedName("password")
    @Expose
    var password : String? = null
)
