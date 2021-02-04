package com.mjceo.transportes.loginsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mjceo.transportes.R;

import java.util.UUID;

public class Profile extends AppCompatActivity {

    FirebaseAuth fAuth;
    DocumentReference userRef;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imgUri = data.getData();
            picture.setImageURI(imgUri);
            // uploadPicture();

        }
    }

    /*private void uploadPicture() {


        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = userRef.child("images/" + randomKey);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Snackbar.make(findViewById())
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


    }*/

    FirebaseFirestore fStore;
    String userID;
    private Uri imgUri;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        final TextView email = (TextView) findViewById(R.id.mail);
        final TextView id = (TextView) findViewById(R.id.id);
        final TextView name = (TextView) findViewById(R.id.name);
        picture = (ImageView) findViewById(R.id.img);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


        userRef = fStore.collection("Users").document(userID);


        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            email.setText(documentSnapshot.getString("E-mail"));
                            id.setText(documentSnapshot.getString("User ID"));
                            name.setText(documentSnapshot.getString("Nome"));
                        }
                    }
                });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
}