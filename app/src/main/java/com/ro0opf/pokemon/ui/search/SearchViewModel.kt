package com.ro0opf.pokemon.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _pokemonIdAndNamesList = MutableLiveData<List<PokemonIdAndNames>>()
    val pokemonIdAndNamesList: LiveData<List<PokemonIdAndNames>> = _pokemonIdAndNamesList


    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val response = repository.fetchPokemonList()
                _pokemonIdAndNamesList.value = response
            } catch (e: Exception) {

            }
        }
    }
}