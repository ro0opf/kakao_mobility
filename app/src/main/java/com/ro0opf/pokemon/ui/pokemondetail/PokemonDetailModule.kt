package com.ro0opf.pokemon.ui.pokemondetail

import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val pokemonDetailModule = module {
    viewModel { (pokemonIdAndNames: PokemonIdAndNames) ->
        PokemonDetailViewModel(pokemonIdAndNames, get())
    }
}