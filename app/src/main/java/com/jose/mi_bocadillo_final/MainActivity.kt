package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.jose.mi_bocadillo_final.ViewModels.MainActivityViewModel
import com.jose.mi_bocadillo_final.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private val authManager = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        //Mira si hay una sesión Activa
        if (authManager.sesionActiva()) {
            navegarPantallaPrincipal()
        }

        binding.botonLogin.setOnClickListener {
            val email = binding.usuario.text.toString()
            val password = binding.contrasena.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authManager.iniciarSesion(email, password) { success, message ->
                    if (success) {
                        obtenerDatosUsuario(email)
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerDatosUsuario(email: String) {
        viewModel.obtenerUsuarioPorEmail(email)
        viewModel.usuario.observe(this) { user ->
            if (user != null) {
                authManager.guardarUsuario(user)
                Toast.makeText(this, "Bienvenido ${user.rol}", Toast.LENGTH_SHORT).show()
                if (user.rol == "admin") {
                    navegarPantallaPrincipal()
                }else if (user.rol == "alumno") {
                    navegarPantallaPrincipal()
                }
            } else {
                Toast.makeText(this, "Error obteniendo datos del usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navegarPantallaPrincipal() {
        startActivity(Intent(this, PantallaAlumno::class.java))
        finish()
    }
}