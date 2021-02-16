package com.ro0opf.pokemon.repository

import android.util.Log
import com.ro0opf.pokemon.repository.pokemon.*

class Repository(
    private val localPokemonData: LocalPokemonData,
    private val remotePokemonData: RemotePokemonData
) {


    suspend fun fetchPokemonList(): List<PokemonIdAndNames> =
        remotePokemonData.fetchPokemonList().pokemons.map { it.convert() }

    suspend fun fetchPokemonLocationList(): List<PokemonLocation> {
        var pokemonLocationListDto = localPokemonData.fetchPokemonLocationList()

        if (pokemonLocationListDto == null) {
            pokemonLocationListDto = remotePokemonData.fetchPokemonLocationList()
            localPokemonData.pokemonLocationListDto = pokemonLocationListDto
        }
        return pokemonLocationListDto.pokemons.map { it.convert() }
    }

    suspend fun fetchPokemonDetail(id: Int): Result<PokemonDetail> {
        var pokemonDetailDto = localPokemonData.fetchPokemonDetail(id)

        return if (pokemonDetailDto != null) {
            Result.Success(pokemonDetailDto.convert())
        } else {
            when (val result = remotePokemonData.fetchPokemonDetail(id)) {
                is Result.Success -> {
                    pokemonDetailDto = result.data
                    localPokemonData.setPokemonDetailMap(id, pokemonDetailDto)
                    Result.Success(pokemonDetailDto.convert())
                }
                is Result.Error -> {
                    Result.Error(result.exception)
                }
                is Result.Loading -> Result.Loading
            }
        }
    }
}
