import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
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
        if (usuario != null) {
            val pedido = Pedido(
                usuarioId = usuario.uid,
                bocadilloId = bocadillo.id,
                descripcion = bocadillo.descripcion,
                precio = bocadillo.coste,
                fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            )

            viewModelScope.launch {
                try {
                    val response = RetrofitConnect.api.realizarPedido(pedido)
                    if (response.isSuccessful) {
                        _pedidoExitoso.value = true
                    } else {
                        _pedidoExitoso.value = false
                        _errorMessage.value = "Error: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error: ${e.message}"
                }
            }
        } else {
            _errorMessage.value = "Error: No se ha podido obtener el usuario."
        }
    }
}