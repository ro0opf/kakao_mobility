package com.ro0opf.pokemon.ui.pokemondetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.ro0opf.pokemon.data.Repository
import com.ro0opf.pokemon.data.pokemon.PokemonDetail
import com.ro0opf.pokemon.data.pokemon.PokemonIdAndNames
import com.ro0opf.pokemon.data.pokemon.PokemonLocationList
import com.ro0opf.pokemon.data.pokemon.convert
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonIdAndNames: PokemonIdAndNames,
    private val repository: Repository
) :
    ViewModel() {
    val toastEvent = LiveEvent<String>()
    val moveToMapsEvent = LiveEvent<PokemonLocationList>()

    private val _pokemonLocationListLiveData = MutableLiveData<PokemonLocationList>()
    val pokemonLocationListLiveData: LiveData<PokemonLocationList> = _pokemonLocationListLiveData

    private val _pokemonDetailLiveData = MutableLiveData<PokemonDetail>()
    val pokemonDetailLiveData: LiveData<PokemonDetail> = _pokemonDetailLiveData

    fun onLocationButtonClick() {
        val pokemonLocationList = _pokemonLocationListLiveData.value

        if (pokemonLocationList?.locations.isNullOrEmpty()) {
            toastEvent.value = "서식지가 알려져있지 않습니다."
        } else {
            moveToMapsEvent.value = pokemonLocationList
        }
    }

    fun fetchPokemonInfo() {
        viewModelScope.launch {
            val deferredPokemonDetail: Deferred<PokemonDetail?> = async {
                if (_pokemonDetailLiveData.value != null) {
                    return@async null
                }

                try {
                    val response = repository.fetchPokemonDetail(pokemonIdAndNames.id)
                    val pokemonDetail = response.body()!!
                    val imgSrc = if (pokemonDetail.sprites.get("front_default") != null) {
                        pokemonDetail.sprites["front_default"].toString()
                    } else {
                        pokemonDetail.sprites.toList().firstOrNull {
                            it.second != null
                        }?.second.toString()
                    }

                    //                    _pokemonDetailLiveData.value =
                    //                        PokemonDetail(
                    //                            pokemonIdAndNames.id,
                    //                            pokemonIdAndNames.names,
                    //                            pokemonDetail.height,
                    //                            pokemonDetail.weight,
                    //                            pokemonDetail.sprites,
                    //                            imgSrc
                    //                        )

                    return@async PokemonDetail(
                        pokemonIdAndNames.id,
                        pokemonIdAndNames.names,
                        pokemonDetail.height,
                        pokemonDetail.weight,
                        pokemonDetail.sprites,
                        imgSrc
                    )
                } catch (e: Exception) {
                    Log.e("PokemonDetailViewModel", "fetchPokemonDetail >> $e.stackTraceToString()")
                }
            } as Deferred<PokemonDetail?>

            val deferredPokemonLocationList: Deferred<PokemonLocationList?> = async {
                val pokemon = _pokemonLocationListLiveData.value

                if (pokemon != null) {
                    return@async null
                }

                try {
                    val response = repository.fetchPokemonLocationList()
                    val locations = response.body()!!.pokemons
                    val idLocations = locations.filter { it.id == pokemonIdAndNames.id }
                        .map { it.convert() }

                    if (idLocations.isNotEmpty()) {
//                        _pokemonLocationListLiveData.value =
//                            PokemonLocationList(
//                                pokemonIdAndNames.id,
//                                idLocations,
//                                pokemonIdAndNames.names
//                            )

                        return@async PokemonLocationList(
                            pokemonIdAndNames.id,
                            idLocations,
                            pokemonIdAndNames.names
                        )
                    } else {
                        return@async null
                    }
                } catch (e: Exception) {
                    Log.e(
                        "PokemonDetailViewModel",
                        "fetchPokemonLocationList >> $e.stackTraceToString()"
                    )
                }
            } as Deferred<PokemonLocationList?>

            val resPokemonDetail = deferredPokemonDetail.await()
            val resPokemonLocationList = deferredPokemonLocationList.await()

            _pokemonDetailLiveData.value = resPokemonDetail
            _pokemonLocationListLiveData.value = resPokemonLocationList
        }
    }
}
