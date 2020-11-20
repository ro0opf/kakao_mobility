package com.ro0opf.pokemon.data.location

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val lat: Double,
    val lng: Double,
    val id: Int
) : Parcelable {

}

data class LocationResult (
    val pokemons: List<Location>
)
