package com.ro0opf.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.ActivitySearchBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private val pokemonAdapter : PokemonAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchViewModel.fetchPokemonList()
        setAddTextChangedListener()
        setRcvSearchResult(binding.rcvSearchResult)
        setObserve()
    }

    private fun setObserve() {
        binding.lifecycleOwner = this

        searchViewModel.pokemonIdAndNamesList.observe(this, {
            pokemonAdapter.pokemonIdAndNamesList.addAll(it)
            pokemonAdapter.submitList(it)
        })
    }

    private fun setRcvSearchResult(rcv: RecyclerView) {
        rcv.adapter = pokemonAdapter
        rcv.layoutManager = LinearLayoutManager(this)
    }

    private fun setAddTextChangedListener() {
        binding.edtSearch.addTextChangedListener { text ->
            run {
                pokemonAdapter.filter.filter(text.toString())
            }
        }
    }
}