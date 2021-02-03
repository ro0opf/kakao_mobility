package com.ro0opf.pokemon.data.pokemon

import android.os.Parcelable
import com.ro0opf.pokemon.data.location.Location
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val names: List<String>,
    var height: Int,
    var weight: Int,
    var sprites: @RawValue Map<String, Any?>,
    var isCachedDetail: Boolean = false,
    var isCachedLocation: Boolean = false,
    var locations: List<Location>?,
    var imgSrc: String?
) : Parcelable {
}

data class PokemonResult(
    val pokemons: List<Pokemon>
)