package com.mjceo.transportation.services

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.mjceo.transportation.utils.Utilities.createNotificationChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationService : Service() {

    private val TAG = javaClass.simpleName
    private val database = FirebaseFirestore.getInstance()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this, "ChannelID", "Location Service Channel")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val notification = NotificationCompat.Builder(this, "ChannelID").build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Starting Location Service.")
        GlobalScope.launch(Dispatchers.IO) { getLocation() }
        return START_NOT_STICKY
    }

    private fun getLocation() {
        if (PermissionChecker.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED)
            return
        val locationRequest = LocationRequest.create().apply {
            interval = 30000; fastestInterval = 15000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) { locationResult ?: return
                val location = locationResult.lastLocation
                val save: HashMap<String, GeoPoint> = HashMap()
                save["Location"] = GeoPoint(location.latitude, location.longitude)
                database.collection("User Locations")
                    .document(FirebaseAuth.getInstance().currentUser!!.uid).set(save)
                    .addOnFailureListener { Log.e(TAG, "Failed to add Location to Firebase.") }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
    }
}