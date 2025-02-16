package com.jose.mi_bocadillo_final.Models

data class Pedido(
    val usuarioId: String,
    val bocadilloId: Int,
    val cantidad: Int
)