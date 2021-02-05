package com.ro0opf.pokemon.data

import android.util.Log
import com.ro0opf.pokemon.data.pokemon.*

class Repository(
    private val localPokemonDataSource: LocalPokemonDataSource,
    private val remotePokemonDataSource: RemotePokemonDataSource
) {


    suspend fun fetchPokemonList(): List<PokemonIdAndNames> =
        remotePokemonDataSource.fetchPokemonList().pokemons.map { it.convert() }

    suspend fun fetchPokemonLocationList(): List<PokemonLocation> {
        var pokemonLocationListDto = localPokemonDataSource.fetchPokemonLocationList()

        if (pokemonLocationListDto == null) {
            pokemonLocationListDto = remotePokemonDataSource.fetchPokemonLocationList()
            localPokemonDataSource.pokemonLocationListDto = pokemonLocationListDto
        }
        return pokemonLocationListDto.pokemons.map { it.convert() }
    }

    suspend fun fetchPokemonDetail(id: Int): PokemonDetail {
        var pokemonDetailDto = localPokemonDataSource.fetchPokemonDetail(id)

        if (pokemonDetailDto == null) {
            pokemonDetailDto = remotePokemonDataSource.fetchPokemonDetail(id)
            localPokemonDataSource.pokemonDetailMap[id] = pokemonDetailDto
        }

        return pokemonDetailDto.convert()
    }
}