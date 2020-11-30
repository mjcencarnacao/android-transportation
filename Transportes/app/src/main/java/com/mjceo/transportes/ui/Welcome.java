package com.mjceo.transportes.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mjceo.transportes.R;
import com.mjceo.transportes.loginsystem.Login;
import com.mjceo.transportes.ui.MainActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void open(View view) {
        Intent launch = new Intent(this, MainActivity.class);
        if (view.getId() == R.id.start) startActivity(launch);
        finish();
    }
}