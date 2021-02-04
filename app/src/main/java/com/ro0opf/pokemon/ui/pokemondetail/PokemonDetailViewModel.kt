package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonDetail
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.data.pokemon.PokemonLocationList
import com.ro0opf.pokemon.data.pokemon.convert
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonIdAndNames: PokemonIdAndNames,
    private val repository: Repository
) :
    ViewModel() {
    val toastEvent = LiveEvent<String>()
    val moveToMapsEvent = LiveEvent<PokemonLocationList>()

    private val _pokemonLocationListLiveData = MutableLiveData<PokemonLocationList>()
    val pokemonLocationListLiveData: LiveData<PokemonLocationList> = _pokemonLocationListLiveData

    private val _pokemonDetailLiveData = MutableLiveData<PokemonDetail>()
    val pokemonDetailLiveData: LiveData<PokemonDetail> = _pokemonDetailLiveData

    fun onLocationButtonClick() {
        val pokemonLocationList = _pokemonLocationListLiveData.value

        if (pokemonLocationList?.locations.isNullOrEmpty()) {
            toastEvent.value = "서식지가 알려져있지 않습니다."
        } else {
            moveToMapsEvent.value = pokemonLocationList
        }
    }

    fun fetchPokemonInfo() {
        viewModelScope.launch {
            val deferredPokemonDetail = async {
                if (_pokemonDetailLiveData.value != null) {
                    return@async null
                }

                val response = repository.fetchPokemonDetail(pokemonIdAndNames.id)
                val pokemonDetail = response ?: return@async null

                val imgSrc = if (pokemonDetail.sprites.get("front_default") != null) {
                    pokemonDetail.sprites["front_default"].toString()
                } else {
                    pokemonDetail.sprites.toList().firstOrNull {
                        it.second != null
                    }?.second.toString()
                }

                return@async PokemonDetail(
                    pokemonIdAndNames.id,
                    pokemonIdAndNames.names,
                    pokemonDetail.height,
                    pokemonDetail.weight,
                    pokemonDetail.sprites,
                    imgSrc
                )
            }

            val deferredPokemonLocationList = async {
                val pokemon = _pokemonLocationListLiveData.value

                if (pokemon != null) {
                    return@async null
                }

                val response = repository.fetchPokemonLocationList()
                val locations = response?.pokemons ?: return@async null
                val idLocations = locations.filter { it.id == pokemonIdAndNames.id }
                    .map { it.convert() }

                if (idLocations.isNotEmpty()) {
                    return@async PokemonLocationList(
                        pokemonIdAndNames.id,
                        idLocations,
                        pokemonIdAndNames.names
                    )
                } else {
                    return@async null
                }
            }

            try {
                val resPokemonDetail = deferredPokemonDetail.await()
                val resPokemonLocationList = deferredPokemonLocationList.await()

                if (resPokemonDetail != null) {
                    _pokemonDetailLiveData.value = resPokemonDetail
                }

                if (resPokemonLocationList != null) {
                    _pokemonLocationListLiveData.value = resPokemonLocationList
                }
            } catch (e: Exception) {
                Log.e("fetch", e.message.toString())
            }
        }
    }
}
