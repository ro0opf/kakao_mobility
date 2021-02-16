package com.ro0opf.pokemon.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ro0opf.pokemon.repository.Repository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(
    private val repository: com.ro0opf.pokemon.repository.Repository
) : ViewModel() {
    val pokemonIdAndNamesList = liveData(Dispatchers.IO) {
        emit(repository.fetchPokemonList())
    }
}
