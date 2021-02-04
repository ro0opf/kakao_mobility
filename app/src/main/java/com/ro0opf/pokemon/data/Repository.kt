package com.ro0opf.pokemon.data

import com.ro0opf.pokemon.common.RetrofitService
import com.ro0opf.pokemon.data.pokemon.*

class Repository(
    private val localPokemonDataSource: LocalPokemonDataSource,
    private val remotePokemonDataSource: RemotePokemonDataSource
) {
    private val pokemonClient = RetrofitService.createService(PokemonAPI::class.java)
    private val pokemonOfficialClient =
        RetrofitService.createPokemonOfficialService(PokemonOfficialAPI::class.java)
    private val pokemonDetailMap = HashMap<Int, PokemonDetail>()
    private var pokemonLocationDTOs: PokemonLocationDTOs? = null

    suspend fun fetchPokemonList() = pokemonClient.fetchPokemonList()

    suspend fun fetchPokemonLocationList(): PokemonLocationDTOs? {
        val pokemonLocationList = localPokemonDataSource.fetchPokemonLocationList()
        if (pokemonLocationList != null){
            return pokemonLocationList
        } else{
            val resPokemonLocationList = remotePokemonDataSource.fetchPokemonLocationList()
        }
    }

    suspend fun fetchPokemonDetail(id: Int): PokemonDetail? {
        if (!pokemonDetailMap.containsKey(id)) {
            val resPokemonDetail = pokemonOfficialClient.fetchPokemonDetail((id)).body()
            if (resPokemonDetail != null) {
                pokemonDetailMap.put(id, resPokemonDetail)
            }
        }
        return pokemonDetailMap[id]
    }
}