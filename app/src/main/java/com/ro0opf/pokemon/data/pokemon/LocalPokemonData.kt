package com.ro0opf.pokemon.data.pokemon

class LocalPokemonData : LocalPokemonDataSource {
    var pokemonLocationListDto : PokemonLocationListDto? = null
    var pokemonDetailMap = HashMap<Int, PokemonDetailDto>()

    override suspend fun fetchPokemonLocationList(): PokemonLocationListDto? {
        return pokemonLocationListDto
    }

    override suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto? {
        return pokemonDetailMap[id]
    }
}