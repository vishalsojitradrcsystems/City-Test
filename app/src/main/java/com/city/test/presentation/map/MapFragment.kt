package com.city.test.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.city.test.R
import com.city.test.databinding.FragmentMapBinding
import com.city.test.domain.model.Coord
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.maps.model.LatLng as LatLng1

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fragmentPhotosBinding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModels()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPhotosBinding = FragmentMapBinding.inflate(inflater)
        fragmentPhotosBinding.mapViewModel = viewModel
        setupMap()
        return fragmentPhotosBinding.root
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    companion object {
        const val KEY_ALBUM_ID = "KEY_ALBUM_ID"
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var location: LatLng1? = LatLng1(0.0, 0.0)
        if (arguments?.containsKey(KEY_ALBUM_ID) == true) {
            val coord =
                Gson().fromJson(arguments?.getString(KEY_ALBUM_ID), Coord::class.java)
            coord?.let {
                location = it.lat?.let { lat -> it.lon?.let { lon -> LatLng1(lat, lon) } }
            }
        }

        // Add a marker in Sydney and move the camera

        location?.let { itLication ->
            mMap.addMarker(
                MarkerOptions()
                    .position(itLication)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(itLication))
        }
    }

}