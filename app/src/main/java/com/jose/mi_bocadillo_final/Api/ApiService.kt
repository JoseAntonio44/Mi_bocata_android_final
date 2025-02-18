package com.jose.mi_bocadillo_final.Api

import com.jose.mi_bocadillo_final.Models.BaseDeDatos
import com.jose.mi_bocadillo_final.Models.Bocadillo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Bocadillo.json")
    suspend fun getBocadillos(): Map<String, Bocadillo>
}