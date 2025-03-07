package com.jose.mi_bocadillo_final.PantallaAdmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

                //Buscar el usuario con el email deseado y obtener su ID en Firebase
                val usuarioId = usuariosResponse.entries.find { it.value.email == email }?.key
                println("ID del usuario a eliminar: $usuarioId")

                if (usuarioId != null) {
                    val response = RetrofitConnect.api.eliminarUsuario(usuarioId)
                    if (response.isSuccessful) {
                        cargarUsuarios()
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

    fun modificarUsuario(usuario: Usuario, email: String) {
        viewModelScope.launch {
            try {
                println("Iniciando modificación de usuario...")

                val usuariosResponse = withContext(Dispatchers.IO) {
                    RetrofitConnect.api.getUsuarios()
                }
                println("Usuarios obtenidos correctamente: $usuariosResponse")

                val usuarioId = usuariosResponse.entries.find { it.value.email == email }?.key
                println("ID del usuario a modificar: $usuarioId")

                if (usuarioId != null) {
                    val response = RetrofitConnect.api.modificarUsuario(usuarioId, usuario)
                    if (response.isSuccessful) {
                        val usuarioModificado = response.body()
                        if (usuarioModificado != null) {
                            cargarUsuarios()
                        } else {
                            _error.postValue("Error: No se recibió respuesta del servidor al modificar el usuario.")
                        }
                    } else {
                        _error.postValue("Error al modificar el usuario: ${response.message()}")
                    }
                } else {
                    _error.postValue("Usuario no encontrado con el email: ${usuario.email}")
                }
            } catch (e: Exception) {
                _error.postValue("Error al modificar el usuario: ${e.message}")
                println("Excepción en modificarUsuario: ${e.message}")
            }
        }
    }

    fun crearUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val response = RetrofitConnect.api.crearUsuario(usuario)
                if (response.isSuccessful) {
                    cargarUsuarios()
                } else {
                    _error.postValue("Error al crear el usuario: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error al crear el usuario: ${e.message}")
            }
        }
    }

}