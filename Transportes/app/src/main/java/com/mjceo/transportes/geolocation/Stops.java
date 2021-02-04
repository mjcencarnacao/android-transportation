package com.mjceo.transportes.geolocation;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mjceo.transportes.R;

public class Stops extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.6476028061038, -9.095667588506203)).title("Terminal Fluvial do Seixal"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.7003003620862, -9.0060001550804)).title("Terminal Fluvial do Montijo"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.65200150857735, -9.078989628815373)).title("Terminal Fluvial do Barreiro"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.688231240151104, -9.14758532897868)).title("Terminal Fluvial de Cacilhas"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.70527627236915, -9.14552145966832)).title("Terminal Fluvial do Cais do Sodré"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.70704346846212, -9.13361913066479)).title("Terminal Fluvial do Terreiro do Paço"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.695138948176684, -9.198511357814759)).title("Terminal Fluvial de Belém"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(38.67709987141529, -9.206943644324063)).title("Terminal Fluvial do Porto Brandão"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.65200150857735, -9.078989628815373), 10f));
    }
}