package com.ro0opf.pokemon.repository.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {
    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    fun <T> createPokemonOfficialService(serviceClass: Class<T>): T {
        return retrofitPokemonOfficial.create(serviceClass)
    }
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://demo0928971.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitPokemonOfficial =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
