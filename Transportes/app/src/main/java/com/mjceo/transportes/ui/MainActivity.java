package com.mjceo.transportes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mjceo.transportes.loginsystem.Login;
import com.mjceo.transportes.R;

public class MainActivity extends AppCompatActivity {

    private Boolean mLocationPermissionsGranted = false;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocationPermission();
    }

    public void open(View view) {
        Intent launch = null;
        switch (view.getId()) {
            case R.id.help:
                launch = new Intent(this, HelpTTSL.class);
                break;
            case R.id.plan:
                launch = new Intent(this, timetable.class);
                break;
            case R.id.comprar:
                launch = new Intent(this, titulos.class);
                break;
            case R.id.stops:
                if (checkServices())
                    launch = new Intent(this, Stops.class);
                break;
            case R.id.track:
                if (checkServices())
                    launch = new Intent(this, RealTime.class);
                break;
            case R.id.account:
                launch = new Intent(this, Login.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        if (launch != null)
            startActivity(launch);
        else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }

    public boolean checkServices() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS)
            return true;
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
            GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST).show();
        else
            Toast.makeText(this, "Por favor verifique os serviços do Google Play.", Toast.LENGTH_LONG).show();
        return false;
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }
}