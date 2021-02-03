package com.ro0opf.pokemon.di

import android.app.Application
import com.ro0opf.pokemon.ui.pokemondetail.pokemonDetailModule
import com.ro0opf.pokemon.ui.search.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@PokemonApplication)
            modules(searchModule, commonModule, pokemonDetailModule)
        }
    }
}