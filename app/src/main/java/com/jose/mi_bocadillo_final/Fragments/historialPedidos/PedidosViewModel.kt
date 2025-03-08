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

    private val _ultimoPedido = MutableLiveData<Pedido?>()
    val ultimoPedido: LiveData<Pedido?> get() = _ultimoPedido

    private val authManager = AuthManager()

    //Metodo para cargar los pedidos del usuario autenticado
    fun cargarPedidos() {
        val usuarioId = authManager.obtenerUsuarioActual()?.uid
        if (usuarioId != null) {
            viewModelScope.launch {
                try {
                    val response = RetrofitConnect.api.getPedidos()
                    val pedidosEncontrados = response.filter { it.value.usuarioId == usuarioId }
                        .values
                        .sortedByDescending { it.fecha }

                    _pedidos.postValue(pedidosEncontrados)

                    //Asignar el último pedido si existe
                    _ultimoPedido.postValue(pedidosEncontrados.firstOrNull())
                } catch (e: Exception) {
                    _pedidos.postValue(null)
                    _ultimoPedido.postValue(null)
                }
            }
        }
    }
}