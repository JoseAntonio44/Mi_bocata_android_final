package com.jose.mi_bocadillo_final.PantallaAdmin

import UsuarioAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jose.mi_bocadillo_final.AuthManager
import com.jose.mi_bocadillo_final.Fragments.FormularioAlumno.FormularioAlumnoFragment
import com.jose.mi_bocadillo_final.MainActivity
import com.jose.mi_bocadillo_final.Models.Usuario
import com.jose.mi_bocadillo_final.R
import com.jose.mi_bocadillo_final.databinding.ActivityMainBinding
import com.jose.mi_bocadillo_final.databinding.ActivityPantallaAdminBinding

class PantallaAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaAdminBinding
    private lateinit var adapter: UsuarioAdapter
    private val usuarioViewModel: PantallaAdminViewModel by viewModels()
    private val authManager = AuthManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)


        adapter = UsuarioAdapter(emptyList(), clickEliminar = { usuario ->
            usuarioViewModel.eliminarUsuario(usuario.email) // Pasa el email
        }, clickEditar = { usuario ->

            mostrarFormularioAlumno(usuario)
            // Lógica para editar usuario
        })

        binding.listaUsuarios.layoutManager = LinearLayoutManager(this)
        binding.listaUsuarios.adapter = adapter


        usuarioViewModel.usuarios.observe(this, Observer { usuarios ->
            adapter.actualizarLista(usuarios ?: emptyList())
        })
        usuarioViewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        binding.botonCerrarSesion.setOnClickListener {
            authManager.cerrarSesion()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        usuarioViewModel.cargarUsuarios()


    }

    private fun mostrarFormularioAlumno(alumno: Usuario? = null) {
        val fragment = FormularioAlumnoFragment()

        // Si estamos editando, pasar los datos del alumno al fragmento
        val bundle = Bundle()
        bundle.putString("nombre", alumno?.nombre)
        bundle.putString("apellidos", alumno?.apellidos)
        bundle.putString("email", alumno?.email)
        bundle.putString("rol", alumno?.rol)
        bundle.putString("password", alumno?.password)
        bundle.putString("curso", alumno?.curso)

        fragment.arguments = bundle

        // Mostrar el fragmento
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment) // Asegúrate de que 'container' es el id del contenedor de fragmentos en tu actividad
        transaction.addToBackStack(null)  // Añadir a la pila de retroceso
        transaction.commit()

        // Asegurar que el fragmento está visible después de añadirse
        supportFragmentManager.executePendingTransactions() // Espera a que el fragmento se añada completamente

        val fragmentView = fragment.view
        fragmentView?.visibility = View.VISIBLE
    }
}