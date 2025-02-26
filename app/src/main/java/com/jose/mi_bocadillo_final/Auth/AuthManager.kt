package com.jose.mi_bocadillo_final

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jose.mi_bocadillo_final.Models.Usuario

class AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun iniciarSesion(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, auth.currentUser?.email)
                } else {
                    Log.e("Auth", "Error en el inicio de sesión", task.exception)
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun guardarUsuario(usuario: Usuario) {
        auth.createUserWithEmailAndPassword(usuario.email, usuario.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "Usuario creado: ${auth.currentUser?.email}")
                } else {
                    Log.e("Auth", "Error al crear el usuario", task.exception)
                }
            }
    }

    fun cerrarSesion() {
        auth.signOut()
        Log.d("Auth", "Usuario ha cerrado sesión")
    }

    fun sesionActiva(): Boolean {
        return auth.currentUser != null
    }


    fun obtenerUsuarioActual(): FirebaseUser? {
        return auth.currentUser
    }

}
