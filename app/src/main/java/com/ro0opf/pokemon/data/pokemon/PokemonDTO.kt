package com.ro0opf.pokemon.data.pokemon


data class PokemonDetailDto(
    val id: Int,
    val names: List<String>,
    val height: Int,
    val weight: Int,
    val sprites: Map<String, Any?>,
    val imgSrc: String?
)

data class PokemonIdAndNamesDto(
    val id: Int,
    val names: List<String>
)

data class PokemonIdAndNamesListDto(
    val pokemons: List<PokemonIdAndNamesDto>
)

data class PokemonLocationDto(
    val lat: Double,
    val lng: Double,
    val id: Int,
)

data class PokemonLocationListDto(
    val pokemons: List<PokemonLocationDto>
)