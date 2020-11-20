package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.Pokemon
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val _isFetchPokemonDetail = MutableLiveData<Boolean>()
    val isFetchPokemonDetail: LiveData<Boolean> = _isFetchPokemonDetail

    fun fetchPokemonDetail(pokemon: Pokemon) {
        viewModelScope.launch {
            try {
                val response = Repository.fetchPokemonDetail(pokemon.id)
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
                _isFetchPokemonDetail.value = true
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "fetchPokemonDetail >> $e.stackTraceToString()")
            }
        }
    }

    fun fetchPokemonLocationList(pokemon: Pokemon) {
        viewModelScope.launch {
            try {
                val response = Repository.fetchPokemonLocationList()
                val locations = response.body()!!.pokemons
                val idLocations = locations.filter { it.id == pokemon.id }

                if (idLocations.isNotEmpty()) {
                    pokemon.locations = idLocations
                    pokemon.isCachedLocation = true
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
