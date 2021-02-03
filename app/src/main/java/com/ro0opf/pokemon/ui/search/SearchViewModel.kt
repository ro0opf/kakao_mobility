package com.ro0opf.pokemon.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.Pokemon
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel() : ViewModel(), KoinComponent {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList : LiveData<List<Pokemon>> = _pokemonList
    val repository by inject<Repository>()

    fun fetchPokemonList(){
        viewModelScope.launch {
            try{
                val response = repository.fetchPokemonList()
                _pokemonList.value = response.body()!!.pokemons
            }catch (e : Exception){

            }
        }
    }
}