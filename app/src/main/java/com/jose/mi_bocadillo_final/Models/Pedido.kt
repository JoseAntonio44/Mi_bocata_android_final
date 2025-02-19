package com.jose.mi_bocadillo_final.Models

data class Pedido(
    val usuarioId: String,
    val bocadilloId: String,
    val descripcion: String,
    val precio: Double,
    val fecha: String
)