package com.ro0opf.pokemon.repository.pokemon

import com.ro0opf.pokemon.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonOfficialAPI {
    @GET("/api/v2/pokemon/{id}")
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetailDto
}
