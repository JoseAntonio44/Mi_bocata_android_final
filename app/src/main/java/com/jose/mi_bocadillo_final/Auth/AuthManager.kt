package com.jose.mi_bocadillo_final

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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
                    Log.d("Auth", "Inicio de sesión exitoso: ${auth.currentUser?.email}")

                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userRef = db.child("Usuario").child(uid)
                        userRef.get().addOnSuccessListener { snapshot ->
                            if (!snapshot.exists()) {
                                val userMap = mapOf(
                                    "email" to email,
                                    "password" to password //TODO se puede hashear en un futuro
                                )
                                userRef.setValue(userMap)
                                    .addOnSuccessListener {
                                        Log.d("Auth", "Usuario registrado en Realtime Database")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("Auth", "Error al registrar usuario en Realtime Database", e)
                                    }
                            }
                        }.addOnFailureListener { e ->
                            Log.e("Auth", "Error al verificar usuario en Realtime Database", e)
                        }
                    }

                    onResult(true, auth.currentUser?.email)
                } else {
                    Log.e("Auth", "Error en el inicio de sesión", task.exception)
                    onResult(false, task.exception?.message)
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
    fun obtenerUsuarioActual(): String? {
        return auth.currentUser?.email
    }
}
