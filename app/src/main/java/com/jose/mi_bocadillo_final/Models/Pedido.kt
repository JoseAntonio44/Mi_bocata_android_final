package com.jose.mi_bocadillo_final.Models

import java.util.Date

data class Pedido(
    val usuarioId: String,
    val bocadilloId: Int,
    val cantidad: Int,
    val fecha: Date
)