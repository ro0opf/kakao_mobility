package com.ro0opf.pokemon.data.pokemon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val id: Int,
    val names: List<String>,
    var height: Int,
    var weight: Int,
    var sprites: @RawValue Map<String, Any?>,
    var isCachedDetail: Boolean = false,
    var isCachedLocation: Boolean = false,
    var locations: List<PokemonLocation>?,
    var imgSrc: String?
) : Parcelable

@Parcelize
data class PokemonDetail(
    val id: Int,
    val names: List<String>,
    val height: Int,
    val weight: Int,
    val sprites: @RawValue Map<String, Any?>,
    var imgSrc: String?
) : Parcelable

@Parcelize
data class PokemonIdAndNames(
    val id: Int,
    val names: List<String>
) : Parcelable

@Parcelize
data class PokemonLocation(
    val lat: Double,
    val lng: Double,
    val id: Int,
) : Parcelable

@Parcelize
data class PokemonLocationList(
    val id: Int,
    val locations: List<PokemonLocation>,
    val names: List<String>
) : Parcelable

fun PokemonLocationDTO.convert(): PokemonLocation {
    return PokemonLocation(
        lat, lng, id
    )
}

fun PokemonIdAndNamesDTO.convert(): PokemonIdAndNames {
    return PokemonIdAndNames(
        id, names
    )
}
