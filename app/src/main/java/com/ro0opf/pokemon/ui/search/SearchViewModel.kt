package com.ro0opf.pokemon.ui.search

import com.airbnb.mvrx.*
import com.ro0opf.pokemon.di.PokemonContext
import com.ro0opf.pokemon.repository.Repository
import com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames

data class PokemonIdAndNamesState(
    val pokemonIdAndNamesListAsync: Async<List<PokemonIdAndNames>> = Uninitialized
) : MavericksState

class SearchViewModel(
    initialState: PokemonIdAndNamesState,
    private val repository: Repository
) : MavericksViewModel<PokemonIdAndNamesState>(initialState) {

    fun getPokemonIdAndNamesList() = withState {
        suspend {
            repository.fetchPokemonList()
        }.execute { async ->
            copy(pokemonIdAndNamesListAsync = async)
        }
    }

    companion object : MavericksViewModelFactory<SearchViewModel, PokemonIdAndNamesState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: PokemonIdAndNamesState
        ): SearchViewModel {
            val koin = PokemonContext.instance.koin
            return SearchViewModel(
                initialState = state,
                repository = koin.get()
            )
        }
    }
}
