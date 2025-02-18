import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jose.mi_bocadillo_final.Api.RetrofitConnect
import com.jose.mi_bocadillo_final.Models.Bocadillo
import kotlinx.coroutines.launch

class PedirBocadilloViewModel : ViewModel() {

    private val _bocadillos = MutableLiveData<List<Bocadillo>>()
    val bocadillos: LiveData<List<Bocadillo>> get() = _bocadillos

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
}