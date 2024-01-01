package com.skripsi.perpusta.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.skripsi.perpusta.data.result.Result
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseRepository {

    suspend fun <T> safeApiCall (
        apiCall: suspend () -> T
    ) : Result<T> {
        return withContext(Dispatchers.IO){
            try {
               Result.Success(apiCall.invoke())
            }catch (throwable: Throwable) {
                when(throwable){
                    is HttpException -> {
                        Result.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Result.Failure(true, null, null)
                    }
                }
            }
        }
    }

}