package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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

    private var canAutenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private fun SetupBiometricAuth() {

        val biometricManager = BiometricManager.from(this)

        val canAuthenticate = biometricManager.canAuthenticate(Authenticators.BIOMETRIC_STRONG)

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            canAutenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Iniciar Sesión con Huella")
                .setDescription("Autenticación Biométrica")
                .setAllowedAuthenticators(Authenticators.BIOMETRIC_STRONG)
                .setNegativeButtonText("Cancelar")
                .build()
        }
    }

    private fun AuthenticateHuella(auth: (auth: Boolean) -> Unit) {
        if (canAutenticate){
            BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    auth(true)
                }
            }).authenticate(promptInfo)
        }else{
            auth(true)
        }
    }
}