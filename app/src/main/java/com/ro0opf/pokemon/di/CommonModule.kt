package com.ro0opf.pokemon.di

import com.ro0opf.pokemon.data.Repository
import org.koin.dsl.module


val commonModule = module {
    single {
        Repository()
    }
}