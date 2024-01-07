package com.skripsi.perpusta.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.perpusta.data.network.RemoteDataSource
import com.skripsi.perpusta.model.circulation.CirculationRequest
import com.skripsi.perpusta.model.circulation.account.AccountResponse
import com.skripsi.perpusta.model.circulation.account.Data
import kotlinx.coroutines.launch

class FineViewModel : ViewModel() {
    private val _circulationList = MutableLiveData<List<Data?>>()
    val circulationList: LiveData<List<Data?>> get() = _circulationList

    fun loadData(npm: String) {
        Log.d("FineViewModel", "Loading data for npm: $npm")
        val circulationRequest = CirculationRequest(npm = npm)
        viewModelScope.launch {
            try {
                val apiService = RemoteDataSource().createApiService()
                val response = apiService.getCirculationAccount(circulationRequest)

                if (response.isSuccessful) {
                    val accountResponse = response.body()
                    Log.d("FineViewModel", "API response: $accountResponse")
                    _circulationList.value = response.body()?.data ?: emptyList()
                    Log.d("FineViewModel", "Circulation List Size: ${_circulationList.value?.size}")
                } else {
                    Log.e("FineViewModel", "API error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("FineViewModel", "Error: ${e.message}", e)
            }
        }
    }

}