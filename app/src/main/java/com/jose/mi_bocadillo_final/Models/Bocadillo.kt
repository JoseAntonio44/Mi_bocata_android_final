package com.jose.mi_bocadillo_final.Models

data class Bocadillo(
    val id: String,
    val coste: Double,
    val descripcion: String,
    val lista_alergenos: List<String>,
)