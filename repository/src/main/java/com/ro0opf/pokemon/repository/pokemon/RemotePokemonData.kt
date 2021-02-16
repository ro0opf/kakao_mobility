package com.ro0opf.pokemon.repository.pokemon

import com.ro0opf.pokemon.repository.Result

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

    override suspend fun fetchPokemonDetail(id: Int): Result<PokemonDetailDto> {
        return try {
            Result.Success(pokemonOfficialClient.fetchPokemonDetail(id))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
