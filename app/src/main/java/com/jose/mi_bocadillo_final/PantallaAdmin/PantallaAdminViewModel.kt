package com.jose.mi_bocadillo_final.PantallaAdmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.launch

class PantallaAdminViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Usuario>?>()
    val usuarios: LiveData<List<Usuario>?> get() = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val authManager = AuthManager()

    //Metodo para cargar los pedidos del usuario autenticado
    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.getUsuarios()
                val usuariosEncontrados = response.values.toList()
                _usuarios.postValue(usuariosEncontrados)
            } catch (e: Exception) {
                _error.postValue("Error al cargar los usuarios: ${e.message}")
            }
        }
    }
}