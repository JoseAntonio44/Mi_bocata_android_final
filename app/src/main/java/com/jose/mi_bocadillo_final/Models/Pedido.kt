package com.jose.mi_bocadillo_final.Models

data class Pedido(
    val id: String,
    val descripcion: String,
    val fecha: String,
    val precio: Double,
    val usuarioId: String ,
    val bocadilloId: String
)