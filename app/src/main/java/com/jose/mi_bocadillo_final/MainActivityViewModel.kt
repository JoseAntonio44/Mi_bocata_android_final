package com.jose.mi_bocadillo_final.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    fun obtenerUsuarioPorEmail(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.getUsuarios()
                val usuarioEncontrado = response.values.find { it.email == email }
                _usuario.postValue(usuarioEncontrado)
            } catch (e: Exception) {
                _usuario.postValue(null)
            }
        }
    }
}