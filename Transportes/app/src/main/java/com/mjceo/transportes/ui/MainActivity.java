package com.mjceo.transportes.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mjceo.transportes.geolocation.RealTime;
import com.mjceo.transportes.geolocation.Stops;
import com.mjceo.transportes.loginsystem.Login;
import com.mjceo.transportes.R;
import com.mjceo.transportes.loginsystem.Profile;
import com.mjceo.transportes.utils.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Menu menu;
    Dialog myDialog;
    DrawerLayout drawerLayout;
    private boolean turist, local = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView textView = findViewById(R.id.textView);
        Toolbar toolbar = findViewById(R.id.x);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        getLocationPermission();
    }

    public void open(View view) {
        switch (view.getId()) {
            case R.id.help:
                startActivity(new Intent(this, HelpTTSL.class));
                break;
            case R.id.plan:
                startActivity(new Intent(this, timetable.class));
                break;
            case R.id.comprar:
                startActivity(new Intent(this, titulos.class));
                break;
            case R.id.stops:
                if (checkServices())
                    startActivity(new Intent(this, Stops.class));
                break;
            case R.id.track:
                if (checkServices())
                    startActivity(new Intent(this, RealTime.class));
                break;
            case R.id.account:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    /////////////////////////////////////////////////////////  DRAWER  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.local:
                turist = false; local = true;
                myDialog = new Dialog(this);
                ShowPopup("popup_local");
                break;
            case R.id.turist:
                turist = true; local = false;
                myDialog = new Dialog(this);
                ShowPopup("popup_turist");
                break;
            case R.id.nav_login:
                startActivity(new Intent(this, Login.class));
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    menu.findItem(R.id.nav_logout).setVisible(true);
                    menu.findItem(R.id.nav_profile).setVisible(true);
                    menu.findItem(R.id.nav_login).setVisible(false);
                }
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);
                menu.findItem(R.id.nav_login).setVisible(true);
                break;
            case R.id.nav_share:
            case R.id.report:
                startActivity(getPackageManager().getLaunchIntentForPackage("com.android.vending"));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.about:
                myDialog = new Dialog(this);
                ShowPopup("popup_about");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void ShowPopup(String popup) {
        int layoutId = getResources().getIdentifier(popup, "layout", getPackageName());
        TextView txtclose;
        myDialog.setContentView(layoutId);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_login).setVisible(false);
        } else {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
        }
    }

    /////////////////////////////////////////////////////////  SERVICES  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public boolean checkServices() {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this) == ConnectionResult.SUCCESS)
            return true;
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this)))
            GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this), 9001).show();
        else
            Toast.makeText(this, "Por favor verifique os serviços do Google Play.", Toast.LENGTH_LONG).show();
        return false;
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else
            ActivityCompat.requestPermissions(this, permissions, 1234);
    }
}