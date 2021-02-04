package com.ro0opf.pokemon.ui.pokemondetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.databinding.DialogPokemonDetailBinding
import com.ro0opf.pokemon.ui.maps.MapsActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PokemonDetailDialogFragment : DialogFragment(), KoinComponent {
    private lateinit var binding: DialogPokemonDetailBinding
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pokemonIdAndNames = arguments?.getParcelable<PokemonIdAndNames>("pokemonIdAndNames")

        if (pokemonIdAndNames == null) {
            Toast.makeText(requireContext(), "pokemon is null", Toast.LENGTH_SHORT).show()
            dismiss()
            return null
        }

        pokemonDetailViewModel = get { parametersOf(pokemonIdAndNames) }
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_pokemon_detail, container, false)

        pokemonDetailViewModel.fetchPokemonInfo()

        setObserve()
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.btnDone.setOnClickListener {
            dismiss()
        }

        binding.btnLocation.setOnClickListener {
            pokemonDetailViewModel.onLocationButtonClick()
        }
    }

    private fun setObserve() {
        binding.lifecycleOwner = this

        pokemonDetailViewModel.toastEvent.observe(this, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        pokemonDetailViewModel.moveToMapsEvent.observe(this, {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("pokemonLocationList", it)
            startActivity(intent)
        })

        pokemonDetailViewModel.pokemonDetailLiveData.observe(this, {
            binding.pokemonDetail = it
        })
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
