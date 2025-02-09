package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.jose.mi_bocadillo_final.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = AuthManager()

        val email = binding.usuario.text
        val password = binding.contrasena.text
        val loginButton = binding.botonLogin

        loginButton.setOnClickListener {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                authManager.iniciarSesion(email.toString(),
                    password.toString()
                ) { success, errorMessage ->
                    if (success) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, PantallaAlumno::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        if (authManager.sesionActiva()) {
            val intent = Intent(this, PantallaAlumno::class.java)
            startActivity(intent)
        }

    }

}