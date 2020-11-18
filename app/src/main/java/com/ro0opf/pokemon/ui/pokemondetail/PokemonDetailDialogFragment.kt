package com.ro0opf.pokemon.ui.pokemondetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.data.pokemon.Pokemon
import com.ro0opf.pokemon.databinding.DialogPokemonDetailBinding
import com.ro0opf.pokemon.ui.maps.MapsActivity

class PokemonDetailDialogFragment(private val pokemon: Pokemon) : DialogFragment() {
    private lateinit var binding: DialogPokemonDetailBinding
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        pokemonDetailViewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_pokemon_detail, container, false)

        if (!pokemon.isCachedDetail) {
            pokemonDetailViewModel.fetchPokemonDetail(pokemon.id)
        }

        if (!pokemon.isCachedLocation) {
            pokemonDetailViewModel.fetchPokemonLocationList(pokemon.id)
        }
        setObserve()
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.btnDone.setOnClickListener {
            dismiss()
        }

        binding.btnLocation.setOnClickListener {
            if (pokemon.locations.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "서식지가 알려져있지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(requireContext(), MapsActivity::class.java)
                intent.putExtra("pokemon", pokemon)
                startActivity(intent)
            }
        }
    }

    private fun setObserve() {
        binding.lifecycleOwner = this
        binding.pokemon = pokemon

        pokemonDetailViewModel.pokemonDetail.observe(this, { it ->
            pokemon.height = it.get("height").asInt
            pokemon.weight = it.get("weight").asInt

            it.getAsJsonObject("sprites").run {
                if (!this.get("front_default").isJsonNull) {
                    pokemon.front_default = get("front_default").asString
                }
            }
            it.getAsJsonObject("sprites").run {
                this.keySet().forEach { key ->
                    if (!this.get(key).isJsonNull) {
                        pokemon.sprites = this[key].asString
                        return@run
                    }
                }
            }

            pokemon.isCachedDetail = true
            binding.pokemon = pokemon
        })

        pokemonDetailViewModel.pokemonLocation.observe(this, {
            pokemon.locations = it
            pokemon.isCachedLocation = true
        })
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
