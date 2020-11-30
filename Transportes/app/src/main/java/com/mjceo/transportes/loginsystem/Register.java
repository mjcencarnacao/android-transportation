package com.mjceo.transportes.loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mjceo.transportes.R;
import com.mjceo.transportes.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    String userID;
    private EditText mail;
    private EditText pass;
    private EditText name;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
    }

    public void open(View view) {
        if (view.getId() == R.id.register) {
            final String email = mail.getText().toString();
            final String password = pass.getText().toString();
            final String nome = name.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter an e-mail address", Toast.LENGTH_LONG);
                return;
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = db.collection("users").document(userID);
                                Map<String, Object> save = new HashMap<>();
                                save.put("Nome: ", nome);
                                save.put("E-mail: ", email);
                                save.put("User ID ", userID);
                                documentReference.set(save).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                });
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });


        } else
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
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