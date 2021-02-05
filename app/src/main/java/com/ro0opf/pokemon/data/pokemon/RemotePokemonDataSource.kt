package com.ro0opf.pokemon.data.pokemon

import com.ro0opf.pokemon.common.RetrofitService

class RemotePokemonDataSource : PokemonDataSource {
    private val pokemonClient = RetrofitService.createService(PokemonAPI::class.java)
    private val pokemonOfficialClient =
        RetrofitService.createPokemonOfficialService(PokemonOfficialAPI::class.java)

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