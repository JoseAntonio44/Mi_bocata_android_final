package com.jose.mi_bocadillo_final.Api

import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.Models.Usuario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("Usuarios.json")
    suspend fun getUsuarios(): Map<String, Usuario>

    @GET("Bocadillo.json")
    suspend fun getBocadillos(): Map<String, Bocadillo>

    @POST("Pedidos.json")
    suspend fun realizarPedido(@Body pedido: Pedido): Response<Pedido>

    @GET("Pedidos.json")
    suspend fun getPedidos(): Map<String, Pedido>



}