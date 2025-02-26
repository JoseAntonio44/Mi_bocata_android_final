package com.jose.mi_bocadillo_final

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jose.mi_bocadillo_final.Models.Usuario
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthManager {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: DatabaseReference = FirebaseDatabase.getInstance("https://proyectobocadillosandroid-default-rtdb.europe-west1.firebasedatabase.app/").reference

    //Iniciar sesion
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

    //Cerrar sesión
    fun cerrarSesion() {
        auth.signOut()
        Log.d("Auth", "Usuario ha cerrado sesión")
    }

    //Ve si hay una sesión activa
    fun sesionActiva(): Boolean {
        return auth.currentUser != null
    }

    //Obtiene el email del usuario actual
    fun obtenerUsuarioActual(): FirebaseUser? {
        return auth.currentUser
    }

}
