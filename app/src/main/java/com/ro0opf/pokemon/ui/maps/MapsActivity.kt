package com.ro0opf.pokemon.ui.maps

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.common.base.BaseActivity
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MapsActivity : BaseActivity(R.layout.activity_maps), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val pokemonLocationList =
            intent.getParcelableExtra<com.ro0opf.pokemon.repository.pokemon.PokemonLocationList>("pokemonLocationList")!!

        val locationList = pokemonLocationList.locations
        val firstZoom = LatLng(locationList[0].lat, locationList[0].lng)

        for (location in locationList) {
            mMap.addMarker(
                MarkerOptions().position(LatLng(location.lat, location.lng))
                    .title(pokemonLocationList.names[0])
            )
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(firstZoom))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f))
    }
}
