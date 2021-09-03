package com.ro0opf.pokemon.ui.pokemondetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import coil.load
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.DialogPokemonDetailBinding
import com.ro0opf.pokemon.di.PokemonKoinComponent
import com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.ui.maps.MapsActivity
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@KoinApiExtension
class PokemonDetailDialogFragment : DialogFragment(), PokemonKoinComponent {
    private lateinit var binding: DialogPokemonDetailBinding
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pokemonIdAndNames =
            arguments?.getParcelable<PokemonIdAndNames>("pokemonIdAndNames")

        if (pokemonIdAndNames == null) {
            Toast.makeText(requireContext(), "pokemon is null", Toast.LENGTH_SHORT).show()
            dismiss()
            return null
        }

        pokemonDetailViewModel = get { parametersOf(pokemonIdAndNames) }
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = DialogPokemonDetailBinding.inflate(
            inflater,
            container,
            false
        )

//        binding.isProgressBarVisible = pokemonDetailViewModel.isProgressBarVisible
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
        pokemonDetailViewModel.toastEvent.observe(this, {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        })

        pokemonDetailViewModel.moveToMapsEvent.observe(this, {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("pokemonLocationList", it)
            startActivity(intent)
        })

        pokemonDetailViewModel.closeEvent.observe(this, {
            dismiss()
        })

        pokemonDetailViewModel.pokemonDetailLiveData.observe(this, {
            it.names?.let { names ->
                binding.tvName.text = "${names[0]} (${names[1]})"
            }
            it.weight?.let { weight ->
                binding.tvWeight.text = "몸무게 : $weight"
            }
            it.height?.let { height ->
                binding.tvHeight.text = "키 : $height"
            }
            binding.ivImg.load(it.imgSrc)
        })
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
