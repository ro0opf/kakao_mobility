package com.ro0opf.pokemon.ui.pokemondetail

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonDetail
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.data.pokemon.PokemonLocationList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonIdAndNames: PokemonIdAndNames,
    private val repository: Repository
) :
    ViewModel() {
    val toastEvent = LiveEvent<String>()
    val moveToMapsEvent = LiveEvent<PokemonLocationList>()

    private val _isProgressBarVisible = MutableLiveData<Boolean>(false)
    val isProgressBarVisible: LiveData<Boolean> = _isProgressBarVisible

    val pokemonDetailLiveData = liveData(Dispatchers.IO) {
        val pokemonDetail = repository.fetchPokemonDetail(pokemonIdAndNames.id)
        val imgSrc = if (pokemonDetail.sprites["front_default"] != null) {
            pokemonDetail.sprites["front_default"].toString()
        } else {
            pokemonDetail.sprites.toList().firstOrNull {
                it.second != null
            }?.second.toString()
        }

        emit(
            PokemonDetail(
                pokemonIdAndNames.id,
                pokemonIdAndNames.names,
                pokemonDetail.height,
                pokemonDetail.weight,
                pokemonDetail.sprites,
                imgSrc
            )
        )
    }
    var job: Job? = null

    fun onLocationButtonClick() {
        job?.cancel()

        job = viewModelScope.launch {
            _isProgressBarVisible.value = true
            val response = repository.fetchPokemonLocationList()
            val idLocations = response.filter { it.id == pokemonIdAndNames.id }
            _isProgressBarVisible.value = false

            if (isActive) {
                if (idLocations.isNotEmpty()) {
                    moveToMapsEvent.value = PokemonLocationList(
                        pokemonIdAndNames.id,
                        idLocations,
                        pokemonIdAndNames.names
                    )
                } else {
                    toastEvent.value = "서식지가 알려져있지 않습니다."
                }
            }
        }
    }
}
