package com.mjceo.transportes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open(View view) {
        Intent launch;
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
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        if (launch != null)
            startActivity(launch);
        else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG);
    }
}