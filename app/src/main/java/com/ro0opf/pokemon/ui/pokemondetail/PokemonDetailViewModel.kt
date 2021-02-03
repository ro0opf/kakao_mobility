package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.Pokemon
import kotlinx.coroutines.launch

class PokemonDetailViewModel(sPokemon: Pokemon, private val repository: Repository) : ViewModel() {
    private val _pokemon = MutableLiveData(sPokemon)
    val pokemon: LiveData<Pokemon> = _pokemon

    val finishEvent = LiveEvent<Unit>()

    val toastEvent = LiveEvent<String>()
    val moveToMapsEvent = LiveEvent<Pokemon>()

    fun onLocationButtonClick() {
        val pokemon = _pokemon.value

        if (pokemon == null) {
            finishEvent.value = Unit
            return
        }

        if (pokemon.locations.isNullOrEmpty()) {
            toastEvent.value = "서식지가 알려져있지 않습니다."
        } else {
            moveToMapsEvent.value = pokemon
        }
    }

    fun fetchPokemonDetail() {
        val pokemon = _pokemon.value

        if (pokemon == null) {
            finishEvent.value = Unit
            return
        }

        if (pokemon.isCachedDetail) {
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.fetchPokemonDetail(pokemon.id)
                val pokemonDetail = response.body()!!

                pokemon.height = pokemonDetail.height
                pokemon.weight = pokemonDetail.weight
                pokemon.sprites = pokemonDetail.sprites

                if (pokemon.sprites["front_default"] != null) {
                    pokemon.imgSrc = pokemon.sprites["front_default"].toString()
                } else {
                    pokemon.sprites.forEach {
                        if (it.value != null) {
                            pokemon.imgSrc = it.value.toString()
                            return@forEach
                        }
                    }
                }
                pokemon.isCachedDetail = true
                Log.e("pokemon", "fetch detail $pokemon")
                _pokemon.value = pokemon

            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "fetchPokemonDetail >> $e.stackTraceToString()")
            }
        }
    }

    fun fetchPokemonLocationList() {
        val pokemon = _pokemon.value

        if (pokemon == null) {
            finishEvent.value = Unit
            return
        }

        if (pokemon.isCachedLocation) {
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.fetchPokemonLocationList()
                val locations = response.body()!!.pokemons
                val idLocations = locations.filter { it.id == pokemon.id }

                if (idLocations.isNotEmpty()) {
                    pokemon.locations = idLocations
                    pokemon.isCachedLocation = true
                    Log.e("pokemon", "fetch location $pokemon")
                    _pokemon.value = pokemon
                }
            } catch (e: Exception) {
                Log.e(
                    "PokemonDetailViewModel",
                    "fetchPokemonLocationList >> $e.stackTraceToString()"
                )
            }
        }
    }
}
