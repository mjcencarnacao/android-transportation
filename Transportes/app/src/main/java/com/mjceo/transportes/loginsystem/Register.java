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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mjceo.transportes.R;
import com.mjceo.transportes.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText mail, name, pass, confirm;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        confirm = (EditText) findViewById(R.id.confirmpassword);
        name = (EditText) findViewById(R.id.name);
    }

    public void open(View view) {
        if (view.getId() == R.id.register) {
            if (TextUtils.isEmpty(mail.getText().toString()) || TextUtils.isEmpty(pass.getText().toString()) || !confirm.getText().toString().equals(pass.getText().toString())) {
                Toast.makeText(this, "Por favor responda corretamente a todos os campos.", Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DocumentReference documentReference = db.collection("Users").document(mAuth.getCurrentUser().getUid());
                                Map<String, Object> save = new HashMap<>();
                                save.put("Nome", name.getText().toString());
                                save.put("E-mail", mail.getText().toString());
                                save.put("User ID", mAuth.getCurrentUser().getUid());
                                documentReference.set(save).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                });
                                startActivity(new Intent(Register.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }
}