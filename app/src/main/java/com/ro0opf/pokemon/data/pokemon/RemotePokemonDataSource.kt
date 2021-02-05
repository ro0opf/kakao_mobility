package com.ro0opf.pokemon.data.pokemon

class RemotePokemonDataSource
    (
    private val pokemonClient: PokemonAPI,
    private val pokemonOfficialClient: PokemonOfficialAPI
) : PokemonDataSource {

    override suspend fun fetchPokemonList(): PokemonIdAndNamesListDto {
        return pokemonClient.fetchPokemonList().body()!!
    }

    override suspend fun fetchPokemonLocationList(): PokemonLocationListDto {
        return pokemonClient.fetchPokemonLocationList().body()!!
    }

    override suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto {
        return pokemonOfficialClient.fetchPokemonDetail(id).body()!!
    }
}