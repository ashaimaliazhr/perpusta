package com.skripsi.perpusta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.model.circulation.CirculationRequest
import com.skripsi.perpusta.model.circulation.account.AccountResponse
import com.skripsi.perpusta.model.circulation.history.HistoryResponse
import com.skripsi.perpusta.model.circulation.status.StatusResponse
import kotlinx.coroutines.launch

class CirculationViewModel : ViewModel(){

    private val _circulationHistoryLength = MutableLiveData<Int>()
    val circulationHistoryLength: LiveData<Int> get() = _circulationHistoryLength

    private val _circulationStatusLength = MutableLiveData<Int>()
    val circulationStatusLength: LiveData<Int> get() = _circulationStatusLength

//    private val _circulationAccountLength = MutableLiveData<List<AccountResponse?>>()
//    val circulationAccountLength: LiveData<List<AccountResponse?>> get() = _circulationAccountLength

    private val _circulationAccountLength = MutableLiveData<Int>()
    val circulationAccountLength: LiveData<Int> get() = _circulationAccountLength

    private val _dueDate = MutableLiveData<String?>()
    val dueDate: LiveData<String?> get() = _dueDate

    fun getCirculationHistory(npm:  String) {
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationHistory(circulationRequest)

                if (response.isSuccessful) {
                    val historyResponse = response.body()
                    if (historyResponse != null) {
                        val historyLength = historyResponse.length ?: 0
                        _circulationHistoryLength.value = historyLength
                    } else {
                        Log.e("CirculationViewModel", "EmptyResponse or missing length")
                    }
                } else {
                    Log.e("CirculationViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CirculationViewModel", "Error: ${e.message}" , e)
            }
        }
    }

    fun getCirculationStatus(npm:  String) {
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationStatus(circulationRequest)

                if (response.isSuccessful) {
                    val statusResponse = response.body()
                    if (statusResponse != null) {
                        val statusLength = statusResponse.length ?: 0
                        _circulationStatusLength.value = statusLength
                    } else {
                        Log.e("CirculationViewModel", "EmptyResponse or missing length")
                    }
                } else {
                    Log.e("CirculationViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CirculationViewModel", "Error: ${e.message}" , e)
            }
        }
    }

    fun getCirculationAccount(npm:  String) {
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationAccount(circulationRequest)

                if (response.isSuccessful) {
                    val accountResponse = response.body()
                    if (accountResponse != null) {
                        val accountLength = accountResponse.length ?: 0
                        _circulationAccountLength.value = accountLength
                    } else {
                        Log.e("CirculationViewModel", "EmptyResponse or missing length")
                    }
                } else {
                    Log.e("CirculationViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CirculationViewModel", "Error: ${e.message}" , e)
            }
        }
    }

    fun fetchDueDate(npm: String){
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationStatus(circulationRequest)

                if (response.isSuccessful) {
                    val statusResponse = response.body()
                    val dataList = statusResponse?.data

                    val dueDate = dataList?.firstOrNull()?.dueDate

                    if (!dueDate.isNullOrEmpty()) {
                        _dueDate.value = dueDate
                    } else {
                        _dueDate.value = null
                    }
                }else {
                    Log.e("CirculationViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CirculationViewModel", "Error: ${e.message}", e)
            }
        }
    }
}