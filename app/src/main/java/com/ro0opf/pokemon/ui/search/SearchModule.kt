package com.ro0opf.pokemon.ui.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val searchModule: Module = module {
    viewModel {
        SearchViewModel(get())
    }

    factory {
        PokemonAdapter()
    }
}

