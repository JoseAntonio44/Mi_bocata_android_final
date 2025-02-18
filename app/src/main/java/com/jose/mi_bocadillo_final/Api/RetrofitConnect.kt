package com.jose.mi_bocadillo_final.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Llamada Retrofit para gestionar la solicitudes */

object RetrofitConnect {
    private const val BASE_URL = "https://proyectobocadillosandroid-default-rtdb.europe-west1.firebasedatabase.app/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

