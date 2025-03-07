package com.jose.mi_bocadillo_final.Api

import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.Models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("Usuarios.json")
    suspend fun getUsuarios(): Map<String, Usuario>

    @GET("Bocadillo.json")
    suspend fun getBocadillos(): Map<String, Bocadillo>

    @POST("Pedidos.json")
    suspend fun realizarPedido(@Body pedido: Pedido): Response<Pedido>

    @GET("Pedidos.json")
    suspend fun getPedidos(): Map<String, Pedido>

    @DELETE("Usuarios/{id}.json")
    suspend fun eliminarUsuario(@Path("id") id: String): Response<Void>

    @POST("Usuarios.json")
    suspend fun crearUsuario(@Body usuario: Usuario): Response<Usuario>

    @PUT("Usuarios/{uid}.json")
    suspend fun modificarUsuario(@Path("uid") id: String, @Body usuario: Usuario): Response<Usuario>


}