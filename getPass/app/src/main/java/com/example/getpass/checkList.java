package com.example.getpass;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class checkList extends AppCompatActivity {
    FirebaseFirestore firestore;
    ArrayList<String> myArraylist = new ArrayList<>();
    ArrayAdapter<String> myAdapter;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        setTitle("WebSites");

        ListView myList = (ListView) findViewById(R.id.siteList);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myArraylist);

        myList.setAdapter(myAdapter);


        CollectionReference collRef = FirebaseFirestore.getInstance().collection("Websites");
        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(!myArraylist.isEmpty()){
                    myArraylist.clear();
                    myAdapter.notifyDataSetChanged();
                }
                for(QueryDocumentSnapshot qdocSnap : value){
                    String id = qdocSnap.getId().toString();
                    myArraylist.add(id);
                    myAdapter.notifyDataSetChanged();
                }
            }
        });


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String st = myList.getAdapter().getItem(i).toString();
                Intent intent = new Intent(checkList.this, infoActivity.class);
                intent.putExtra("postion",st);
                startActivity(intent);
            }
        });

















//        firestore.collection("Websites").document("").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    String value = documentSnapshot.getString("SiteName");
//                    myArraylist.add(value);
//                    myAdapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
//                }else
//                    Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
//            }
//        });

//        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Websites").document("sgmnSwAzZfbClNa7JB80");


//        CollectionReference collRef = FirebaseFirestore.getInstance().collection("b  ");

//        collRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                myArraylist.add(queryDocumentSnapshots.getDocuments().toString());
//                myAdapter.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(), "Succses",Toast.LENGTH_LONG).show();
//            }
//        });

//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    myArraylist.add(documentSnapshot.getString("SiteName"));
//                    myAdapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();
//                }else
//                    Toast.makeText(getApplicationContext(),"Does not Exist",Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"Failure to get Collection", Toast.LENGTH_LONG).show();
//            }
//        });


    }
}