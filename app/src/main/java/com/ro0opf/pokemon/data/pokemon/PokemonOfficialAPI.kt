package com.ro0opf.pokemon.data.pokemon

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonOfficialAPI {
    @GET("/api/v2/pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id : Int) : Response<PokemonDetailDto>
}