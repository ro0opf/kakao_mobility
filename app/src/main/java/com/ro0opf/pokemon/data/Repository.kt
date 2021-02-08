package com.ro0opf.pokemon.data

import com.ro0opf.pokemon.data.pokemon.*

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

    suspend fun fetchPokemonDetail(id: Int): PokemonDetail {
        var pokemonDetailDto = localPokemonData.fetchPokemonDetail(id)

        if (pokemonDetailDto == null) {
            pokemonDetailDto = remotePokemonData.fetchPokemonDetail(id)
            localPokemonData.setPokemonDetailMap(id, pokemonDetailDto)
        }

        return pokemonDetailDto.convert()
    }
}