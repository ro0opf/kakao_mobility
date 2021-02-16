package com.ro0opf.pokemon.repository.pokemon

import com.ro0opf.pokemon.repository.Result
import retrofit2.http.Path

interface RemotePokemonDataSource {
    suspend fun fetchPokemonList(): PokemonIdAndNamesListDto
    suspend fun fetchPokemonLocationList(): PokemonLocationListDto
    suspend fun fetchPokemonDetail(@Path("id") id: Int): Result<PokemonDetailDto>
}
