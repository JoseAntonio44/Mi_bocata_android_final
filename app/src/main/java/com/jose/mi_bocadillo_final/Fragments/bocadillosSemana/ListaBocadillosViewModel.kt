package com.jose.mi_bocadillo_final.Fragments.bocadillosSemana

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.Models.Bocadillo
import kotlinx.coroutines.launch

class ListaBocadillosViewModel : ViewModel() {

    private val _bocatas = MutableLiveData<List<Bocadillo>>()
    val bocatas: LiveData<List<Bocadillo>> get() = _bocatas

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchBocadillos() {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.getBocadillos()
                val diaMap = mapOf(
                    "lunes" to 1,
                    "martes" to 2,
                    "miércoles" to 3,
                    "jueves" to 4,
                    "viernes" to 5,
                    "sábado" to 6,
                    "domingo" to 7
                )
                val bocadillosOrdenados = response.values.toList().sortedBy { diaMap[it.dia] }
                _bocatas.value = bocadillosOrdenados
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }
}
