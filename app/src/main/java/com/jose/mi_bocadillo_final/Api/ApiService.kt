package com.jose.mi_bocadillo_final.Api

import com.jose.mi_bocadillo_final.Models.Bocadillo
import com.jose.mi_bocadillo_final.Models.Pedido
import com.jose.mi_bocadillo_final.Models.ResponseMessage
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    interface ApiService {
        @GET("bocadillos")
        suspend fun getBocadillos(): List<Bocadillo>  // Devuelve una lista de bocadillos

        @GET("bocadillo/{id}")
        suspend fun getBocadilloById(@Path("id") id: Int): Bocadillo


        @POST("pedido")
        suspend fun hacerPedido(@Body pedido: Pedido): ResponseMessage
    }
}