package com.jose.mi_bocadillo_final.Models

data class BaseDeDatos (
    val bocadillos: List<Bocadillo>,
    val CalendarioBocadillo: Map<String, CalendarioBocadillo> = mapOf(),
    val Usuario: Map<String, Usuario> = mapOf()
)