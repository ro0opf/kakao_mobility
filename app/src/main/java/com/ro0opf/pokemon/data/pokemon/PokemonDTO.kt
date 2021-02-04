package com.ro0opf.pokemon.data.pokemon

data class PokemonIdAndNamesDTO(
    val id: Int,
    val names: List<String>
)

data class PokemonIdAndNamesDTOs(
    val pokemons: List<PokemonIdAndNamesDTO>
)

data class PokemonLocationDTO(
    val lat: Double,
    val lng: Double,
    val id: Int,
)

data class PokemonLocationDTOs(
    val pokemons: List<PokemonLocationDTO>
)