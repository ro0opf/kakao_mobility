package com.ro0opf.pokemon.repository.pokemon

interface LocalPokemonDataSource {
    suspend fun fetchPokemonLocationList(): PokemonLocationListDto?
    suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto?
}
