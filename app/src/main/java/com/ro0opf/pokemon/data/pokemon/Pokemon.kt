package com.ro0opf.pokemon.data.pokemon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PokemonDetail(
    val id: Int?,
    val names: List<String>?,
    val height: Int?,
    val weight: Int?,
    val sprites: @RawValue Map<String, Any?>,
    val imgSrc: String?
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

fun PokemonDetailDto.convert() : PokemonDetail {
    return PokemonDetail(
        id, names, height, weight, sprites, imgSrc
    )
}

fun PokemonLocationDto.convert(): PokemonLocation {
    return PokemonLocation(
        lat, lng, id
    )
}

fun PokemonIdAndNamesDto.convert(): PokemonIdAndNames {
    return PokemonIdAndNames(
        id, names
    )
}
