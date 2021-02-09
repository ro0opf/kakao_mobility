package com.ro0opf.pokemon.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonDetail
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.data.pokemon.PokemonLocationList
import kotlinx.coroutines.Dispatchers

class PokemonDetailViewModel(
    private val pokemonIdAndNames: PokemonIdAndNames,
    private val repository: Repository
) :
    ViewModel() {
    val toastEvent = LiveEvent<String>()
    val moveToMapsEvent = LiveEvent<PokemonLocationList>()

    val pokemonLocationListLiveData = liveData(Dispatchers.IO) {
        val response = repository.fetchPokemonLocationList()
        val idLocations = response.filter { it.id == pokemonIdAndNames.id }

        if (idLocations.isNotEmpty()) {
            emit(
                PokemonLocationList(
                    pokemonIdAndNames.id,
                    idLocations,
                    pokemonIdAndNames.names
                )
            )
        }
    }

    val pokemonDetailLiveData = liveData(Dispatchers.IO) {
        val pokemonDetail = repository.fetchPokemonDetail(pokemonIdAndNames.id)
        val imgSrc = if (pokemonDetail.sprites["front_default"] != null) {
            pokemonDetail.sprites["front_default"].toString()
        } else {
            pokemonDetail.sprites.toList().firstOrNull {
                it.second != null
            }?.second.toString()
        }

        emit(
            PokemonDetail(
                pokemonIdAndNames.id,
                pokemonIdAndNames.names,
                pokemonDetail.height,
                pokemonDetail.weight,
                pokemonDetail.sprites,
                imgSrc
            )
        )
    }

    fun onLocationButtonClick() {
        val pokemonLocationList = pokemonLocationListLiveData.value

        if (pokemonLocationList?.locations.isNullOrEmpty()) {
            toastEvent.value = "서식지가 알려져있지 않습니다."
        } else {
            moveToMapsEvent.value = pokemonLocationList
        }
    }
}
