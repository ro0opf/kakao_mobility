package com.ro0opf.pokemon.data.pokemon

class LocalPokemonDataSource : PokemonDataSource {
    var pokemonLocationListDto : PokemonLocationListDto? = null
    var pokemonDetailMap = HashMap<Int, PokemonDetailDto>()

    override suspend fun fetchPokemonList(): PokemonIdAndNamesListDto {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPokemonLocationList(): PokemonLocationListDto? {
        return pokemonLocationListDto
    }

    override suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto? {
        return pokemonDetailMap[id]
    }
}