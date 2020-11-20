package com.ro0opf.pokemon.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private val pokemonAdapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        searchViewModel.fetchPokemonList()
        setAddTextChangedListener()
        setRcvSearchResult(binding.rcvSearchResult)
        setObserve()
    }

    private fun setObserve() {
        binding.lifecycleOwner = this
        searchViewModel.pokemonList.observe(this, {
            pokemonAdapter.pokemonList.addAll(it)
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