package com.jose.mi_bocadillo_final

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    // Iniciar sesión con correo y contraseña
    fun iniciarSesion(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Inicio de sesión exitoso: ${auth.currentUser?.email}")
                    onResult(true, auth.currentUser?.email)
                } else {
                    Log.e("Auth", "Error en el inicio de sesión", task.exception)
                    onResult(false, task.exception?.message)
                }
            }
    }

    // Cerrar sesión
    fun cerrarSesion() {
        auth.signOut()
        Log.d("Auth", "Usuario ha cerrado sesión")
    }

    // Verificar si hay una sesión activa
    fun sesionActiva(): Boolean {
        return auth.currentUser != null
    }

    // Obtener el usuario actual
    fun obtenerUsuarioActual(): String? {
        return auth.currentUser?.email
    }
}
