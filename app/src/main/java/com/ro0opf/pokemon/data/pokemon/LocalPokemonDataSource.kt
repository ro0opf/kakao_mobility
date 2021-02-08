package com.ro0opf.pokemon.data.pokemon

interface LocalPokemonDataSource {
    suspend fun fetchPokemonLocationList(): PokemonLocationListDto?
    suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto?
}
