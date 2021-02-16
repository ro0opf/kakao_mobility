package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.repository.Result
import kotlinx.coroutines.*

class PokemonDetailViewModel(
    private val pokemonIdAndNames: com.ro0opf.pokemon.repository.pokemon.PokemonIdAndNames,
    private val repository: com.ro0opf.pokemon.repository.Repository
) :
    ViewModel() {
    val toastEvent = LiveEvent<String>()
    val closeEvent = LiveEvent<Unit>()
    val moveToMapsEvent = LiveEvent<com.ro0opf.pokemon.repository.pokemon.PokemonLocationList>()

    private val _isProgressBarVisible = MutableLiveData(false)
    val isProgressBarVisible: LiveData<Boolean> = _isProgressBarVisible

    val pokemonDetailLiveData = liveData(Dispatchers.IO) {
        when (val result = repository.fetchPokemonDetail(pokemonIdAndNames.id)) {
            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    _isProgressBarVisible.value = false
                }
                val pokemonDetail = result.data
                val imgSrc = if (pokemonDetail.sprites["front_default"] != null) {
                    pokemonDetail.sprites["front_default"].toString()
                } else {
                    pokemonDetail.sprites.toList().firstOrNull {
                        it.second != null
                    }?.second.toString()
                }

                emit(
                    com.ro0opf.pokemon.repository.pokemon.PokemonDetail(
                        pokemonIdAndNames.id,
                        pokemonIdAndNames.names,
                        pokemonDetail.height,
                        pokemonDetail.weight,
                        pokemonDetail.sprites,
                        imgSrc
                    )
                )
            }
            is Result.Error -> {
                _isProgressBarVisible.postValue(false)
                toastEvent.postValue("${result.exception.message}")
                closeEvent.postValue(Unit)
            }
            is Result.Loading -> _isProgressBarVisible.value = true
        }
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
                    moveToMapsEvent.value =
                        com.ro0opf.pokemon.repository.pokemon.PokemonLocationList(
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
