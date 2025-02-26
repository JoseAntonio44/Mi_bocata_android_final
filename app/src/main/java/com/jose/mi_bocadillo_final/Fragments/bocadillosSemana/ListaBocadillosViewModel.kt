package com.jose.mi_bocadillo_final.Fragments.bocadillosSemana

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

    fun fetchBocatas() {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.getBocadillos()
                //print(response.listIterator(0))

               // _bocatas.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
