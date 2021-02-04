package com.ro0opf.pokemon.data.pokemon

import retrofit2.Response

interface PokemonDataSource {
    suspend fun fetchPokemonList(): PokemonIdAndNamesDTOs
    suspend fun fetchPokemonLocationList(): PokemonLocationDTOs
}