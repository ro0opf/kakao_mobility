package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.repository.common.RetrofitService
import org.koin.dsl.module


val commonModule = module {
    single {
        com.ro0opf.pokemon.repository.Repository(get(), get())
    }

    single {
        com.ro0opf.pokemon.repository.pokemon.LocalPokemonData()
    }

    single {
        com.ro0opf.pokemon.repository.pokemon.RemotePokemonData(get(), get())
    }

    single {
        get<RetrofitService>().createService(com.ro0opf.pokemon.repository.pokemon.PokemonAPI::class.java)
    }

    single {
        get<RetrofitService>().createPokemonOfficialService(com.ro0opf.pokemon.repository.pokemon.PokemonOfficialAPI::class.java)
    }

    single {
        RetrofitService()
    }
}
