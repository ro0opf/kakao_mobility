package com.ro0opf.pokemon.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ro0opf.pokemon.data.pokemon.Pokemon
import com.ro0opf.pokemon.databinding.ItemPokemonBinding
import com.ro0opf.pokemon.ui.pokemondetail.PokemonDetailDialogFragment
import java.util.*
import kotlin.collections.ArrayList

class PokemonAdapter(private val pokemonList: MutableList<Pokemon>) :
    ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon):
                Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon):
                Boolean = oldItem == newItem
    }), Filterable {

    private var filterList: List<Pokemon> = pokemonList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPokemonBinding =
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(filterList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filterList = if (charString.isEmpty()) {
                    pokemonList
                } else {
                    val filteredList = ArrayList<Pokemon>()
                    for (pokemon in pokemonList) {
                        if (pokemon.names[0].contains(charString.toLowerCase(Locale.ROOT))
                            || pokemon.names[1].contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(pokemon)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterList = filterResults.values as List<Pokemon>
                submitList(filterList)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.clPokemon.setOnClickListener {
                val dialog = PokemonDetailDialogFragment(pokemon)
                dialog.show((binding.root.context as SearchActivity).supportFragmentManager, "pokemonDetailDialogFragment")
            }
        }
    }
}