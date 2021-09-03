package com.ro0opf.pokemon.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.ro0opf.pokemon.di.PokemonKoinComponent

abstract class BaseFragment : Fragment, MavericksView, PokemonKoinComponent {
    constructor() : super()
    constructor(@LayoutRes layoutResourceId: Int) : super(layoutResourceId)

    override fun invalidate() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
        observe()
    }

    abstract fun view()
    abstract fun observe()
}
