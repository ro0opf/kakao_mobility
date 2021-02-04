package com.ro0opf.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.databinding.ItemPokemonBinding
import com.ro0opf.pokemon.ui.pokemondetail.PokemonDetailDialogFragment
import java.util.*
import kotlin.collections.ArrayList

class PokemonAdapter :
    ListAdapter<PokemonIdAndNames, PokemonAdapter.ViewHolder>(object : DiffUtil.ItemCallback<PokemonIdAndNames>() {
        override fun areItemsTheSame(oldItem: PokemonIdAndNames, newItem: PokemonIdAndNames):
                Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: PokemonIdAndNames, newItem: PokemonIdAndNames):
                Boolean = oldItem == newItem
    }), Filterable {

    var pokemonIdAndNamesList = ArrayList<PokemonIdAndNames>()
    private var filterList: List<PokemonIdAndNames> = pokemonIdAndNamesList

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
                    pokemonIdAndNamesList
                } else {
                    val filteredList = ArrayList<PokemonIdAndNames>()
                    for (pokemonIdAndNames in pokemonIdAndNamesList) {
                        if (pokemonIdAndNames.names[0].toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))
                            || pokemonIdAndNames.names[1].toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(pokemonIdAndNames)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterList = filterResults.values as List<PokemonIdAndNames>
                submitList(filterList)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(pokemonIdAndNames: PokemonIdAndNames) {
            binding.pokemonIdAndNames = pokemonIdAndNames
            binding.clPokemon.setOnClickListener {
                val dialog = PokemonDetailDialogFragment()
                val args = Bundle()
                args.putParcelable("pokemonIdAndNames", pokemonIdAndNames);
                dialog.arguments = args;

                dialog.show((binding.root.context as SearchActivity).supportFragmentManager, "pokemonDetailDialogFragment")
            }
        }
    }
}