import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jose.mi_bocadillo_final.Api.ApiService
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.Models.Usuario
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import java.util.UUID

class PedirBocadilloViewModel : ViewModel() {
    private lateinit var authManager: AuthManager
    private lateinit var bocadillo: Bocadillo


    private val _bocadillos = MutableLiveData<List<Bocadillo>>()
    val bocadillos: LiveData<List<Bocadillo>> get() = _bocadillos

    private val _pedidoExitoso = MutableLiveData<Boolean>()
    val pedidoExitoso: LiveData<Boolean> get() = _pedidoExitoso


    private val _bocadilloHoy = MutableLiveData<String?>()
    val bocadilloHoy: LiveData<String?> get() = _bocadilloHoy

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun fetchBocadillos() {
        Log.d("App", "FETCH")
        viewModelScope.launch {
            try {

                val response = RetrofitConnect.api.getBocadillos()
                _bocadillos.value = response.values.toList()
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun hacerPedido(bocadillo: Bocadillo) {
        val usuario = AuthManager().obtenerUsuarioActual()
        val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (usuario != null) {
            viewModelScope.launch {
                try {
                    //pedidos del usuario autenticado
                    val response = RetrofitConnect.api.getPedidos()
                    val pedidosDelUsuario =
                        response.filter { it.value.usuarioId == usuario.uid }.values.toList()

                    val pedidoHoy = pedidosDelUsuario.any { it.fecha == fechaHoy }

                    if (pedidoHoy) {
                        _pedidoExitoso.value = false
                        _errorMessage.value = "Ya has realizado un pedido hoy."
                        return@launch
                    }

                    //Si no hay pedido hoy crea uno nuevo
                    val pedido = Pedido(
                        id = UUID.randomUUID().toString(),
                        usuarioId = usuario.uid,
                        bocadilloId = bocadillo.id,
                        descripcion = bocadillo.descripcion,
                        precio = bocadillo.coste,
                        fecha = fechaHoy,
                    )

                    val pedidoResponse = RetrofitConnect.api.realizarPedido(pedido)
                    if (pedidoResponse.isSuccessful) {
                        _pedidoExitoso.value = true
                    } else {
                        _pedidoExitoso.value = false
                        _errorMessage.value = "Error: ${pedidoResponse.message()}"
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _errorMessage.value = "Error: No se ha podido obtener el usuario."
        }
    }

    fun cancelarPedido() {
        val usuario = AuthManager().obtenerUsuarioActual()
        val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (usuario != null) {
            viewModelScope.launch {
                try {
                    // Obtener todos los pedidos del usuario autenticado
                    val response = RetrofitConnect.api.getPedidos()
                    val pedidosDelUsuario = response
                        .filter { it.value.usuarioId == usuario.uid }
                        .map { it.key to it.value } // Convertimos a lista de (id, Pedido)

                    // Buscar el pedido de hoy
                    val pedidoHoy = pedidosDelUsuario.find { it.second.fecha == fechaHoy }

                    if (pedidoHoy != null) {
                        val pedidoId = pedidoHoy.first // ID real de Firebase
                        val cancelResponse = RetrofitConnect.api.cancelarPedido(pedidoId)

                        if (cancelResponse.isSuccessful) {
                            _pedidoExitoso.value = true
                        } else {
                            _pedidoExitoso.value = false
                            _errorMessage.value = "Error al cancelar el pedido: ${cancelResponse.message()}"
                        }
                    } else {
                        _pedidoExitoso.value = false
                        _errorMessage.value = "No hay pedido para cancelar hoy."
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _errorMessage.value = "Error: No se ha podido obtener el usuario."
        }
    }


    fun obtenerPedidoBocadilloHoy() {
        val usuario = AuthManager().obtenerUsuarioActual()
        val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (usuario != null) {
            viewModelScope.launch {
                try {
                    // Obtener los pedidos del usuario autenticado
                    val response = RetrofitConnect.api.getPedidos()
                    val pedidosDelUsuario =
                        response.filter { it.value.usuarioId == usuario.uid }.values.toList()

                    // Buscar el pedido de hoy
                    val pedidoHoy = pedidosDelUsuario.find { it.fecha == fechaHoy }

                    if (pedidoHoy != null) {
                        _bocadilloHoy.postValue(pedidoHoy.descripcion) // Guardar el nombre del bocadillo
                    } else {
                        _bocadilloHoy.postValue(null) // No hay pedido hoy
                    }
                } catch (e: Exception) {
                    _bocadilloHoy.postValue(null)
                }
            }
        }
    }

}