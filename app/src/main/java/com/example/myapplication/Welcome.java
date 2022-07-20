package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Welcome extends AppCompatActivity {
    FirebaseFirestore fstore;
    String userId,r;
    FirebaseAuth fAuth;
    TextView textView15,textView16,textView17,textView18;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        textView15=findViewById(R.id.textView15);
        textView16=findViewById(R.id.textView16);
        textView17=findViewById(R.id.textView17);
        textView18=findViewById(R.id.textView18);
        proceed=findViewById(R.id.proceed);
        fAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference=fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                textView15.setText(value.getString("email"));
                textView16.setText(value.getString("fname"));
                textView17.setText(value.getString("lname"));
                textView18.setText(value.getString("Role"));
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r = textView18.getText().toString().trim();
               // if(r=="farmer"){
                    Intent i = new Intent(getApplicationContext(), farmer.class);
                    startActivity(i);
               // }
                 /*if(r=="vet") {
                    Intent i=new Intent(getApplicationContext(),vetmain.class);
                    startActivity(i);
                }*/

            }
        });

    }
}