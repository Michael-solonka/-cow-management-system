package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class vannouncement extends AppCompatActivity {
    private Button buttonSubmitAssmntDetails ;
    private EditText assmntTitle, subjectName, assmntDescription;
    FirebaseUser fire;
    String username;
    String uid;
    DatabaseReference reff;
    FirebaseFirestore fstore;
    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vannouncement);


        buttonSubmitAssmntDetails = findViewById(R.id.buttonSubmitAssmntDetails);

        assmntTitle = findViewById(R.id.assmntTitle);
        subjectName = findViewById(R.id.subjectName);
        assmntDescription = findViewById(R.id.assmntDescription);
        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();
        fstore= FirebaseFirestore.getInstance();
        reff=FirebaseDatabase.getInstance().getReference().child("announcements");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DocumentReference df=fstore.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess"+documentSnapshot.getData());
                username= documentSnapshot.getString("email");
            }
        });

        buttonSubmitAssmntDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String level = assmntTitle.getText().toString();
                String subject = subjectName.getText().toString();
                String description = assmntDescription.getText().toString().trim();
                String  user=username;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("announcements");
                announcehelper l= new announcehelper(level,subject,description,user);

                myRef.child(String.valueOf(maxid+1)).setValue(l);
                Toast.makeText(vannouncement.this,"announcement has been added",Toast.LENGTH_SHORT).show();
                assmntTitle.getText().clear();
                subjectName.getText().clear();
                assmntDescription.getText().clear();

            }
        });



    }
}