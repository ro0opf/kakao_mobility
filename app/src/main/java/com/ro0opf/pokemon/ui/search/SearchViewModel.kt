package com.ro0opf.pokemon.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.data.pokemon.convert
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel() : ViewModel(), KoinComponent {
    private val _pokemonIdAndNamesList = MutableLiveData<List<PokemonIdAndNames>>()
    val pokemonIdAndNamesList: LiveData<List<PokemonIdAndNames>> = _pokemonIdAndNamesList

    private val repository by inject<Repository>()

    fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val response = repository.fetchPokemonList()
                _pokemonIdAndNamesList.value = response.body()?.pokemons
                    ?.map {
                        it.convert()
                    }
            } catch (e: Exception) {

            }
        }
    }
}