package com.ro0opf.pokemon.di

import android.app.Application
import android.content.Context
import com.airbnb.mvrx.Mavericks
import com.ro0opf.pokemon.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this@PokemonApplication)
        PokemonContext.startKoin(
            this@PokemonApplication,
            listOf(commonModule, viewModelModule)
        )

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

object PokemonContext {
    val instance by lazy { KoinApplication.init() }

    fun startKoin(context: Context, modules: List<Module>) {
        instance.androidLogger(Level.ERROR)
        instance.modules(modules)
        instance.androidContext(context)
    }
}

@KoinApiExtension
interface PokemonKoinComponent : KoinComponent {
    override fun getKoin(): Koin = PokemonContext.instance.koin
}
