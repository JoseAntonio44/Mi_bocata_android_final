package com.jose.mi_bocadillo_final.Fragments.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    private val authManager = AuthManager()

    fun cargarUsuarioLogueado() {
        val email = authManager.obtenerUsuarioActual()?.email
        if (email != null) {
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
}