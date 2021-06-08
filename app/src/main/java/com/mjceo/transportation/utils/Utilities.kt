@file:Suppress("SameParameterValue")

package com.mjceo.transportation.utils

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap.createScaledBitmap
import android.graphics.BitmapFactory.decodeResource
import android.os.Build
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.mjceo.transportation.R
import com.mjceo.transportation.services.LocationService
import com.mjceo.transportation.utils.SharedPreferencesManager.customPrefs
import com.mjceo.transportation.utils.models.User
import kotlinx.coroutines.tasks.await
import java.math.BigInteger
import java.security.MessageDigest

object Utilities {

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun getBitmap(drawable: Int, context: Context) =
        createScaledBitmap(decodeResource(context.resources, drawable), 80, 80, false)

    fun setMarkers(map: GoogleMap, lat: Double, long: Double, title: String, context: Context) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style))
        val icon = getBitmap(R.drawable.ic_sailboat, context)
        map.addMarker(MarkerOptions().position(LatLng(lat, long)).title(title))!!
            .setIcon(fromBitmap(icon))
    }

    fun createNotificationChannel(context: Context, id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun startLocationService(activity: Activity, context: Context) {
        val serviceIntent = Intent(context, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            activity.startForegroundService(serviceIntent)
        else
            activity.startService(serviceIntent)
    }

    suspend fun loadCurrentUser(context: Context) {
        val auth = FirebaseAuth.getInstance().currentUser!!.uid
        val document = FirebaseFirestore.getInstance().collection("Users").document(auth).get().await()
        val user = User(document.getString("Email").toString(),document.getString("Password").toString())
        user.name = document.getString("Name").toString()
        customPrefs(context).edit().putString("User", Gson().toJson(user)).apply()
    }

    fun getCurrentUser(context: Context):User{
        val pref = customPrefs(context).getString("User",null)
        return Gson().fromJson(pref,User::class.java)
    }
}