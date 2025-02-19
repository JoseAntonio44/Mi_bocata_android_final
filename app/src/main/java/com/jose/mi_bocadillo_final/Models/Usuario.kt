package com.jose.mi_bocadillo_final.Models

data class Usuario(
    val email: String,
    val password: String,
    val nombre: String,
    val apellidos: String,
    val curso: String,
    val rol: String
)