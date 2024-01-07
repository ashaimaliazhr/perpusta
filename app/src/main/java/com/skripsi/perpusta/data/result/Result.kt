package com.skripsi.perpusta.data.result

import okhttp3.ResponseBody

sealed class Result<out T> {
    data class Success<out T> (val data: T) : Result<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
        ) : Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
