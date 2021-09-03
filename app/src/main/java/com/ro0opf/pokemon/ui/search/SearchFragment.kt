package com.ro0opf.pokemon.ui.search

import androidx.core.widget.addTextChangedListener
import com.airbnb.mvrx.fragmentViewModel
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.common.base.BaseFragment
import com.ro0opf.pokemon.common.util.viewBinding
import com.ro0opf.pokemon.databinding.FragmentSearchBinding
import timber.log.Timber

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val searchViewModel: SearchViewModel by fragmentViewModel()
    private val pokemonAdapter = PokemonAdapter()
    private val binding: FragmentSearchBinding by viewBinding()

    override fun view() {
        searchViewModel.getPokemonIdAndNamesList()

        binding.edtSearch.addTextChangedListener { text ->
            run {
                pokemonAdapter.filter.filter(text.toString())
            }
        }

        binding.rcvSearchResult.adapter = pokemonAdapter
    }

    override fun observe() {
        searchViewModel.onAsync(PokemonIdAndNamesState::pokemonIdAndNamesListAsync, onSuccess = {
            pokemonAdapter.pokemonIdAndNamesList.addAll(it)
            pokemonAdapter.submitList(it)
        }, onFail = {
            Timber.e(it)
        })
    }
}
