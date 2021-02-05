package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.common.RetrofitService
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.LocalPokemonDataSource
import com.ro0opf.pokemon.data.pokemon.PokemonAPI
import com.ro0opf.pokemon.data.pokemon.PokemonOfficialAPI
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
        RemotePokemonDataSource(get(), get())
    }

    single {
        get<RetrofitService>().createService(PokemonAPI::class.java)
    }

    single {
        get<RetrofitService>().createPokemonOfficialService(PokemonOfficialAPI::class.java)
    }

    single {
        RetrofitService()
    }
}