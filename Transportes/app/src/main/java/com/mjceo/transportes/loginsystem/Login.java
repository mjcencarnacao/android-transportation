package com.mjceo.transportes.loginsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mjceo.transportes.R;
import com.mjceo.transportes.ui.MainActivity;
import com.mjceo.transportes.utils.User;

public class Login extends AppCompatActivity {

    private EditText mail, pass;
    private FirebaseAuth mAuth;
    private static User signed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
    }

    public static User getSigned(){
        return signed;
    }

    public void login(View view) {
        switch (view.getId()) {
            case R.id.go:
                if (TextUtils.isEmpty(mail.getText().toString()) || TextUtils.isEmpty(pass.getText().toString())) {
                    Toast.makeText(this, "Please enter an e-mail address", Toast.LENGTH_LONG).show();
                    return;
                }
                Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Welcome back" + mail.getText().toString(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                break;
            case R.id.signup:
                startActivity(new Intent(this, Register.class));
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(Login.this, "Already signed in.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}