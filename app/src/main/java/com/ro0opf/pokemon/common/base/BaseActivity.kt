package com.ro0opf.pokemon.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.MavericksView
import com.ro0opf.pokemon.di.PokemonKoinComponent
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
abstract class BaseActivity(@LayoutRes val contentLayoutId: Int = 0) : PokemonKoinComponent,
    AppCompatActivity(), MavericksView {

    override fun invalidate() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (contentLayoutId != 0) {
            setContentView(contentLayoutId)
        }
    }
}
