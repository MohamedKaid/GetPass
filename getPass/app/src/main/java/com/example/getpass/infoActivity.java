package com.example.getpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class infoActivity extends AppCompatActivity {
    FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        String itemPostion =getIntent().getExtras().getString("postion");
        setTitle(itemPostion.toUpperCase());

        TextView user, pass;
        user = (TextView) findViewById(R.id.tvUser);
        pass = (TextView) findViewById(R.id.tvPass);


        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Websites").document(itemPostion);


        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    user.setText(documentSnapshot.getString("Username"));
                    pass.setText(documentSnapshot.getString("Password"));
                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(),"Does not Exist",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
            }
        });

    }
}