package com.mjceo.transportation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.mjceo.transportation.R
import com.mjceo.transportation.utils.Utilities.setMarkers

class StopsFragment : Fragment(R.layout.fragment_stops), OnMapReadyCallback {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        setMarkers(googleMap,38.6476028061038, -9.095667588506203,"Terminal Fluvial do Seixal",requireContext())
        setMarkers(googleMap,38.7003003620862, -9.0060001550804,"Terminal Fluvial do Montijo",requireContext())
        setMarkers(googleMap,38.65200150857735, -9.078989628815373,"Terminal Fluvial do Barreiro",requireContext())
        setMarkers(googleMap,38.688231240151104, -9.14758532897868,"Terminal Fluvial de Cacilhas",requireContext())
        setMarkers(googleMap,38.70527627236915, -9.14552145966832,"Terminal Fluvial do Cais do Sodré",requireContext())
        setMarkers(googleMap,38.70704346846212, -9.13361913066479,"Terminal Fluvial do Terreiro do Paço",requireContext())
        setMarkers(googleMap,38.695138948176684, -9.198511357814759,"Terminal Fluvial de Belém",requireContext())
        setMarkers(googleMap,38.67709987141529, -9.206943644324063,"Terminal Fluvial do Porto Brandão",requireContext())
        googleMap.moveCamera(newLatLngZoom(LatLng(38.65200150857735, -9.078989628815373), 15f))
    }
}