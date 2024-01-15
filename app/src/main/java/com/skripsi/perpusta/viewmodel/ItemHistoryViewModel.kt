package com.skripsi.perpusta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.model.circulation.CirculationRequest
import com.skripsi.perpusta.model.circulation.history.Data
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ItemHistoryViewModel : ViewModel() {
    private val _circulationHistory = MutableLiveData<List<Data?>>()
    val circulationHistory: LiveData<List<Data?>> get() = _circulationHistory

    fun loadData(npm: String) {
        Log.d("ItemHistoryViewModel", "Loading dat for npm: $npm")
        val circulationRequest = CirculationRequest(npm = npm)

        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationHistory(circulationRequest)

                if (response.isSuccessful){
                    val accountResponse = response.body()
                    Log.d("ItemHistoryViewModel", "API response: $accountResponse")

                    _circulationHistory.value = response.body()?.data ?: emptyList()
                    Log.d("ItemHistoryViewModel", "Circulation History Size: ${_circulationHistory.value?.size}")

                }else {
                    Log.e("ItemHistoryViewModel", "Error: ${response.message()}")
                }

            }catch (e: SocketTimeoutException){
                Log.e("ItemHistoryViewModel", "Socket Timenout: ${e.message}", e)

            }
        }
    }
}