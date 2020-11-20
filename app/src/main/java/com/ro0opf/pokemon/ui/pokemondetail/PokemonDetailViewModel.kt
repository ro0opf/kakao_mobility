package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.location.Location
import com.ro0opf.pokemon.data.pokemon.Pokemon
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val _isFetchPokemonDetail = MutableLiveData<Boolean>()
    val isFetchPokemonDetail : LiveData<Boolean> = _isFetchPokemonDetail

    fun fetchPokemonDetail(pokemon : Pokemon) {
        viewModelScope.launch {
            try {
                val response = Repository.fetchPokemonDetail(pokemon.id)
                val pokemonData : JsonObject = response.body()!!
                pokemon.height = pokemonData.get("height").asInt
                pokemon.weight = pokemonData.get("weight").asInt

                pokemonData.getAsJsonObject("sprites").run {
                    if (!this.get("front_default").isJsonNull) {
                        pokemon.front_default = get("front_default").asString
                    }
                }
                pokemonData.getAsJsonObject("sprites").run {
                    this.keySet().forEach { key ->
                        if (!this.get(key).isJsonNull) {
                            pokemon.sprites = this[key].asString
                            return@run
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
                val locations = Gson().fromJson(response.body()!!["pokemons"], Array<Location>::class.java).toList()
                val idLocations = locations.filter{ it.id == pokemon.id }

                if(idLocations.isNotEmpty()) {
                    pokemon.locations = idLocations
                    pokemon.isCachedLocation = true
                }
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "fetchPokemonLocationList >> $e.stackTraceToString()")
            }
        }
    }
}
