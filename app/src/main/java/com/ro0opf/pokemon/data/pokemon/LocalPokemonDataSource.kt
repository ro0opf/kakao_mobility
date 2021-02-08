package com.ro0opf.pokemon.data.pokemon

import retrofit2.http.Path

interface LocalPokemonDataSource {
    suspend fun fetchPokemonLocationList(): PokemonLocationListDto?
    suspend fun fetchPokemonDetail(@Path("id") id: Int): PokemonDetailDto?
}
