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
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {

    private val _pokemonDetail = MutableLiveData<JsonObject>()
    val pokemonDetail : LiveData<JsonObject> = _pokemonDetail

    private val _pokemonLocation = MutableLiveData<List<Location>>()
    val pokemonLocation : LiveData<List<Location>> = _pokemonLocation

    fun fetchPokemonDetail(id : Int) {
        viewModelScope.launch {
            try {
                val response = Repository.fetchPokemonDetail(id)
                _pokemonDetail.value = response.body()
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "fetchPokemonDetail >> $e.stackTraceToString()")
            }
        }
    }

    fun fetchPokemonLocationList(id : Int) {
        viewModelScope.launch {
            try {
                val response = Repository.fetchPokemonLocationList()
                val locations = Gson().fromJson(response.body()!!["pokemons"], Array<Location>::class.java).toList()
                val idLocations = locations.filter{ it.id == id }

                if(idLocations.isNotEmpty()) {
                    _pokemonLocation.value = idLocations
                }
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "fetchPokemonLocationList >> $e.stackTraceToString()")
            }
        }
    }
}
