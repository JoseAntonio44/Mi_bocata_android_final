package com.jose.mi_bocadillo_final.Api

import com.jose.mi_bocadillo_final.Models.Bocadillo
import retrofit2.http.GET

interface ApiService {
    @GET("Bocadillo")
    suspend fun getBocadillos(): List<Bocadillo>
}