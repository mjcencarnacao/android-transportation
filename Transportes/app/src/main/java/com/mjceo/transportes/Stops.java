package com.mjceo.transportes;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Stops extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng seixal = new LatLng(38.6476028061038, -9.095667588506203);
        LatLng montijo = new LatLng(38.7003003620862, -9.0060001550804);
        LatLng barreiro = new LatLng(38.65200150857735, -9.078989628815373);
        LatLng cacilhas = new LatLng(38.688231240151104, -9.14758532897868);
        LatLng sodre = new LatLng(38.70527627236915, -9.14552145966832);
        LatLng terreiro = new LatLng(38.70704346846212, -9.13361913066479);
        LatLng belem = new LatLng(38.695138948176684, -9.198511357814759);
        LatLng brandão = new LatLng(38.67709987141529, -9.206943644324063);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(seixal).title("Terminal Fluvial do Seixal"));
        mMap.addMarker(new MarkerOptions().position(montijo).title("Terminal Fluvial do Montijo"));
        mMap.addMarker(new MarkerOptions().position(barreiro).title("Terminal Fluvial do Barreiro"));
        mMap.addMarker(new MarkerOptions().position(cacilhas).title("Terminal Fluvial de Cacilhas"));
        mMap.addMarker(new MarkerOptions().position(sodre).title("Terminal Fluvial do Cais do Sodré"));
        mMap.addMarker(new MarkerOptions().position(terreiro).title("Terminal Fluvial do Terreiro do Paço"));
        mMap.addMarker(new MarkerOptions().position(belem).title("Terminal Fluvial de Belém"));
        mMap.addMarker(new MarkerOptions().position(brandão).title("Terminal Fluvial do Porto Brandão"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barreiro, 15f));
    }
}