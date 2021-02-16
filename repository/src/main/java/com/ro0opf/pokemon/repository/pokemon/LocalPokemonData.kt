package com.ro0opf.pokemon.repository.pokemon

class LocalPokemonData : LocalPokemonDataSource {
    var pokemonLocationListDto : PokemonLocationListDto? = null
    private val pokemonDetailMap = HashMap<Int, PokemonDetailDto>()

    override suspend fun fetchPokemonLocationList(): PokemonLocationListDto? {
        return pokemonLocationListDto
    }

    override suspend fun fetchPokemonDetail(id: Int): PokemonDetailDto? {
        return pokemonDetailMap[id]
    }

    fun setPokemonDetailMap(id : Int, pokemonDetailDto: PokemonDetailDto) {
        pokemonDetailMap[id] = pokemonDetailDto
    }
}
