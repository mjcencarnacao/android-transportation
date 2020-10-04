package com.mjceo.transportes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void open(View view) throws InterruptedException {
        Intent launch;
        switch (view.getId()) {
            case R.id.start:
                launch = new Intent(this, MainActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        if (launch != null) {
            TimeUnit.SECONDS.sleep(1);
            startActivity(launch);
        } else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG);
    }
}