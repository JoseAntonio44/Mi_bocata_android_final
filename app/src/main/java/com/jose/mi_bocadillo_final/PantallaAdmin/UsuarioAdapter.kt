import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jose.mi_bocadillo_final.Models.Usuario
import com.jose.mi_bocadillo_final.R
class UsuarioAdapter(
    private var listaAlumnos: List<Usuario>,
    private val clickEliminar: (Usuario) -> Unit,
    private val clickEditar: (Usuario) -> Unit)
    : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombre)
        val apellidos: TextView = view.findViewById(R.id.txtApellidos)
        val email: TextView = view.findViewById(R.id.txtEmail)
        val botonEditar: TextView = view.findViewById(R.id.botonEditar)
        val botonEliminar: TextView = view.findViewById(R.id.botonEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alumno, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alumno = listaAlumnos[position]
        holder.nombre.text = alumno.nombre
        holder.apellidos.text = alumno.apellidos
        holder.email.text = alumno.email
        holder.botonEditar.setOnClickListener {
            clickEditar(alumno)
        }
        holder.botonEliminar.setOnClickListener {
            clickEliminar(alumno)
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size

    fun actualizarLista(nuevaLista: List<Usuario>) {
        listaAlumnos = nuevaLista
        notifyDataSetChanged()
    }
}