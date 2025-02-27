package com.jose.mi_bocadillo_final.PantallaAdmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.launch

class PantallaAdminViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Usuario>?>()
    val usuarios: LiveData<List<Usuario>?> get() = _usuarios
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error


    //Metodo para cargar los pedidos del usuario autenticado
    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.getUsuarios()
                val usuariosEncontrados = response.values.toList()
                _usuarios.postValue(usuariosEncontrados.filter { it.rol == "alumno" })
            } catch (e: Exception) {
                _error.postValue("Error al cargar los usuarios: ${e.message}")
            }
        }
    }

    fun eliminarUsuario(email: String) {
        viewModelScope.launch {
            try {
                val usuariosResponse = RetrofitConnect.api.getUsuarios()

                // Buscar el usuario con el email deseado y obtener su ID en Firebase
                val usuarioId = usuariosResponse.entries.find { it.value.email == email }?.key

                if (usuarioId != null) {
                    val response = RetrofitConnect.api.eliminarUsuario(usuarioId)
                    if (response.isSuccessful) {
                        cargarUsuarios()  // Recargar lista después de eliminar
                    } else {
                        _error.postValue("Error al eliminar el usuario: ${response.message()}")
                    }
                } else {
                    _error.postValue("Usuario no encontrado con el email: $email")
                }
            } catch (e: Exception) {
                _error.postValue("Error al eliminar el usuario: ${e.message}")
            }
        }
    }
}