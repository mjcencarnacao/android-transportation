package com.mjceo.transportation.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.mjceo.transportation.R
import com.mjceo.transportation.utils.Utilities.setMarkers
import com.mjceo.transportation.utils.Utilities.startLocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RealTimeFragment : Fragment(R.layout.fragment_real_time), OnMapReadyCallback {

    private val database = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        startLocationService(requireActivity(), requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED)
            googleMap.isMyLocationEnabled = true
        getLocations(googleMap)
        val update = object : Runnable {
            override fun run() {
                getLocations(googleMap)
                Handler(Looper.getMainLooper()).postDelayed(this, 15000)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(update, 15000)
    }

    private fun getLocations(map: GoogleMap) {
        map.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            val document = database.collection("User Locations").get().await().documents
            document.forEach {
                val location = it.getGeoPoint("Location")!!
                withContext(Dispatchers.Main) {
                    setMarkers(map, location.latitude, location.longitude, "", requireContext())
                }
            }
        }
    }
}
