package com.ro0opf.pokemon.data.pokemon

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.ro0opf.pokemon.data.location.Location
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val names: List<String>,
    var height: Int,
    var weight: Int,
    var front_default: String?,
    var sprites: String?,
    var isCachedDetail: Boolean = false,
    var isCachedLocation: Boolean = false,
    var locations: List<Location>,
) : Parcelable {

}

