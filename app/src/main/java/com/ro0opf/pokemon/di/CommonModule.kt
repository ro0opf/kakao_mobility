package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.LocalPokemonDataSource
import com.ro0opf.pokemon.data.pokemon.RemotePokemonDataSource
import org.koin.dsl.module


val commonModule = module {
    single {
        Repository(get(), get())
    }

    single {
        LocalPokemonDataSource()
    }

    single {
        RemotePokemonDataSource()
    }
}