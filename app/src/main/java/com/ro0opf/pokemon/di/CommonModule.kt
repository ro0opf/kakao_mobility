package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.common.RetrofitService
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.*
import org.koin.dsl.module


val commonModule = module {
    single {
        Repository(get(), get())
    }

    single {
        LocalPokemonData()
    }

    single {
        RemotePokemonData(get(), get())
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