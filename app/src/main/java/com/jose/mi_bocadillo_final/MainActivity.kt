package com.jose.mi_bocadillo_final

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.jose.mi_bocadillo_final.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = AuthManager()

        val email = binding.usuario.text
        val password = binding.contrasena.text
        val loginButton = binding.botonLogin
        val biometricButton = binding.botonHuella


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

        biometricButton.setOnClickListener {
            AuthenticateHuella { auth ->
                if (auth) {
                    val intent = Intent(this, PantallaAlumno::class.java)
                    startActivity(intent)
                }
            }
        }


        if (authManager.sesionActiva()) {
            val intent = Intent(this, PantallaAlumno::class.java)
            startActivity(intent)
        }

        SetupBiometricAuth()

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