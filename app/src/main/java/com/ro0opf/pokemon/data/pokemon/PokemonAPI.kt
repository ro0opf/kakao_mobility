package com.ro0opf.pokemon.data.pokemon

import retrofit2.http.GET

interface PokemonAPI {
    @GET("/pokemon_name")
    suspend fun fetchPokemonList(): PokemonIdAndNamesListDto

    @GET("/pokemon_locations")
    suspend fun fetchPokemonLocationList(): PokemonLocationListDto
}