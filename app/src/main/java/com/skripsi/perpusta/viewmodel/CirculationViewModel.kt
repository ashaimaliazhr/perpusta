package com.skripsi.perpusta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.model.circulation.CirculationRequest
import com.skripsi.perpusta.model.circulation.history.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CirculationViewModel : ViewModel(){

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _circulationHistory = MutableLiveData<List<Data>>()
    val circulationHistory: LiveData<List<Data>> = _circulationHistory

    private val _circulationHistoryLength = MutableLiveData<Int>()
    val circulationHistoryLength: LiveData<Int> = _circulationHistoryLength

    private val _circulationStatusLength = MutableLiveData<Int>()
    val circulationStatusLength: LiveData<Int> = _circulationStatusLength

    private val _circulationAccountLength = MutableLiveData<Int>()
    val circulationAccountLength : LiveData<Int> = _circulationAccountLength

    private val remoteDataSource = RemoteDataSource()

    suspend fun getCirculationHistory(npm: String) {
        try {
            val response = withContext(Dispatchers.IO) {
                remoteDataSource.createApiService().getCirculationHistory(npm)
            }
            if (response.isSuccessful) {
                val historyResponse = response.body()

                historyResponse?.let {
                    _circulationHistory.value = it.data?.filterNotNull() ?: emptyList()

                    _circulationHistoryLength.value = it.length ?: 0
                }
            } else {
                //Handle API Error
                _errorMessage.value = "Error: ${response.message()}"
            }
        }catch (e: Exception) {
            //handle network
            _errorMessage.value = "Error: ${e.message}"
        }
    }

    suspend fun getCirculationStatus(npm: String) {
        try {
            val response = withContext(Dispatchers.IO) {
                remoteDataSource.createApiService().getCirculationStatus(CirculationRequest(npm))
            }
            if (response.isSuccessful) {
                _circulationStatusLength.value = response.body()?.length ?: 0
            } else {
                _errorMessage.value = "Error: ${response.message()}"
            }
        }catch (e: Exception) {
            _errorMessage.value = "Error: ${e.message}"
        }
    }

    suspend fun getCirculationAccount(npm: String) {
        try {
            val response = withContext(Dispatchers.IO) {
                remoteDataSource.createApiService().getCirculationAccount(CirculationRequest(npm))
            }
            if (response.isSuccessful) {
                _circulationAccountLength.value = response.body()?.length ?: 0
            } else {
                _errorMessage.value = "Error: ${response.message()}"
            }
        }catch (e: Exception) {
            _errorMessage.value = "Error: ${e.message}"
        }
    }
}