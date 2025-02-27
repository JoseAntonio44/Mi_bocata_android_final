package com.jose.mi_bocadillo_final.PantallaAdmin

import UsuarioAdapter
import android.content.Intent
import android.os.Bundle
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
}