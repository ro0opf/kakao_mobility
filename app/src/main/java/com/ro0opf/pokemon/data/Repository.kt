package com.ro0opf.pokemon.data

import com.ro0opf.pokemon.common.RetrofitService
import com.ro0opf.pokemon.data.pokemon.PokemonAPI
import com.ro0opf.pokemon.data.pokemon.PokemonOfficialAPI

class Repository{
    private val pokemonClient = RetrofitService.createService(PokemonAPI::class.java)
    private val pokemonOfficialClient = RetrofitService.createPokemonOfficialService(PokemonOfficialAPI::class.java)

    suspend fun fetchPokemonList() = pokemonClient.fetchPokemonList()
    suspend fun fetchPokemonLocationList() = pokemonClient.fetchPokemonLocationList()

    suspend fun fetchPokemonDetail(id : Int) = pokemonOfficialClient.fetchPokemonDetail(id)
}