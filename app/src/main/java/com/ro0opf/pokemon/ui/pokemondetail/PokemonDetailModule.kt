package com.ro0opf.pokemon.ui.pokemondetail

import com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val pokemonDetailModule = module {
    viewModel { (pokemonIdAndNames: com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames) ->
        PokemonDetailViewModel(pokemonIdAndNames, get())
    }
}
