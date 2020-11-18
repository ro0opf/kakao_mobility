package com.ro0opf.pokemon.data.pokemon

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface PokemonAPI {
    @GET("/pokemon_name")
    suspend fun fetchPokemonList() : Response<JsonObject>

    @GET("/pokemon_locations")
    suspend fun fetchPokemonLocationList() : Response<JsonObject>
}