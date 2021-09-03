package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.ui.pokemondetail.PokemonDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (pokemonIdAndNames: PokemonIdAndNames) ->
        PokemonDetailViewModel(pokemonIdAndNames, get())
    }
}
