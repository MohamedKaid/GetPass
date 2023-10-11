package com.example.getpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();

        EditText a, b, c;
        a = findViewById(R.id.site);
        b = findViewById(R.id.user);
        c = findViewById(R.id.pass);
        Button btn = (Button) findViewById(R.id.btn1);
        Button checkList = (Button) findViewById(R.id.btn2);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String site = String.valueOf(a.getText());
                String user = String.valueOf(b.getText());
                String pass = String.valueOf(c.getText());

                CollectionReference websites = firestore.collection("Websites");

                Map<String,Object> passWords = new HashMap<>();
                passWords.put("Username", user);
                passWords.put("Password", pass);
                websites.document(site).set(passWords);

                firestore.collection("Websites").add(passWords).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,checkList.class);
                startActivity(i);
            }
        });


    }
}