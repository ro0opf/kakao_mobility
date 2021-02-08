package com.ro0opf.pokemon.data.pokemon

class RemotePokemonData
    (
    private val pokemonClient: PokemonAPI,
    private val pokemonOfficialClient: PokemonOfficialAPI
) : RemotePokemonDataSource {

    override suspend fun fetchPokemonList(): PokemonIdAndNamesListDto {
        return pokemonClient.fetchPokemonList()
    }

    override suspend fun fetchPokemonLocationList(): PokemonLocationListDto {
        return pokemonClient.fetchPokemonLocationList()
    }

    override suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto {
        return pokemonOfficialClient.fetchPokemonDetail(id)
    }
}