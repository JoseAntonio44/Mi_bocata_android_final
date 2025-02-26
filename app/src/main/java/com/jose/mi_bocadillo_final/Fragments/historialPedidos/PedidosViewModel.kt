package com.jose.mi_bocadillo_final.Fragments.pedidos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.Models.Pedido
import kotlinx.coroutines.launch

class PedidosViewModel : ViewModel() {
    private val _pedidos = MutableLiveData<List<Pedido>?>()
    val pedidos: LiveData<List<Pedido>?> get() = _pedidos

    private val authManager = AuthManager()

    //Metodo para cargar los pedidos del usuario autenticado
    fun cargarPedidos() {
        val usuarioId = authManager.obtenerUsuarioActual()?.uid
        if (usuarioId != null) {
            viewModelScope.launch {
                try {
                    val response = RetrofitConnect.api.getPedidos()
                    val pedidosEncontrados = response.filter {it.value.usuarioId == usuarioId }
                    _pedidos.postValue(pedidosEncontrados.values.toList())
                } catch (e: Exception) {
                    _pedidos.postValue(null)
                }
            }
        }
    }
}