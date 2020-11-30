package com.mjceo.transportes.loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mjceo.transportes.R;
import com.mjceo.transportes.ui.MainActivity;

public class Login extends AppCompatActivity {

    private EditText mail;
    private EditText pass;
    private TextView register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
    }

    public void login(View view) {
        Intent launch = null;
        switch (view.getId()) {
            case R.id.go:
                String email = mail.getText().toString();
                String password = pass.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Please enter an e-mail address", Toast.LENGTH_LONG);
                    return;
                }
                Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Yes", Toast.LENGTH_SHORT).show();
                                    // Sign in success, update UI with the signed-in user's information
                                    // Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
                break;
            case R.id.signup:
                launch = new Intent(this, Register.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        if (launch != null)
            startActivity(launch);
        else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser) {
        Intent launch = new Intent(this, MainActivity.class);
        startActivity(launch);
    }
}