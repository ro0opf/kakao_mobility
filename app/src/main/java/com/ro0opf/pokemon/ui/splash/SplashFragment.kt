package com.ro0opf.pokemon.ui.splash

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.common.base.BaseFragment
import com.ro0opf.pokemon.common.util.viewBinding
import com.ro0opf.pokemon.databinding.FragmentSplashBinding
import com.ro0opf.pokemon.ui.search.SearchActivity
import kotlinx.coroutines.delay
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val binding: FragmentSplashBinding by viewBinding()

    private fun startLoginActivity() {
        lifecycleScope.launchWhenCreated {
            delay(SPLASH_TIME_OUT)
            startActivity(Intent(requireContext(), SearchActivity::class.java))
            activity?.run {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 1500
    }

    override fun view() {
        binding.ivPokemon.load(R.drawable.ic_pokemon)
        startLoginActivity()
    }

    override fun observe() {
    }
}
