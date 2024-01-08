package com.skripsi.perpusta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.model.circulation.CirculationRequest
import com.skripsi.perpusta.model.circulation.status.Data
import kotlinx.coroutines.launch

class ItemBorrowViewModel : ViewModel() {
    private val _circulationList = MutableLiveData<List<Data?>>()
    val circulationList: LiveData<List<Data?>> get() = _circulationList

    fun loadData(npm: String) {
        Log.d("ItemBorrowViewModel", "Loading data from npm: $npm")
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService =RemoteDataSource().createApiService()
                val response = apiService.getCirculationStatus(circulationRequest)

                if (response.isSuccessful) {
                    val statusResponse = response.body()
                    Log.d("ItemBorrowViewModel", "API response: $statusResponse")
                    _circulationList.value = response.body()?.data ?: emptyList()
                    Log.d("ItemBorrowViewModel", "Circulation List Size: ${_circulationList.value?.size}")
                } else {
                    Log.e("ItemBorrowViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ItemBorrowViewModel", "Error: ${e.message}", e)
            }
        }
    }
}