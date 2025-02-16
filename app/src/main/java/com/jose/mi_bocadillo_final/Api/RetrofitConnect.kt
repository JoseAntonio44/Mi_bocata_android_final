package com.jose.mi_bocadillo_final.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Llamada Retrofit para gestionar la solicitudes */

object RetrofitConnect {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

/*
    baseUrl(BASE_URL): Define la URL base de la API.

    addConverterFactory(GsonConverterFactory.create()): Usa Gson para convertir
                                                    automáticamente JSON a objetos Kotlin.
    create(ApiService::class.java): Crea la implementación de ApiService.

 */