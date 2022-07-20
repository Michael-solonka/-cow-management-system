package com.example.myapplication;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class admin extends AppCompatActivity {

    private EditText userFirstName, userLastname, username, userPassword, userRole;
    private Button buttonInsert;
    FirebaseAuth fAuth;
    private ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID,isfarmer,isvet;
    ToggleButton toggleButton;
    DatabaseReference reff;
    long max=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        userFirstName = findViewById(R.id.regUserFirstName);
        userLastname = findViewById(R.id.regUserLastname);
        username = findViewById(R.id.regUsername);
        userPassword = findViewById(R.id.regUserPassword);
        toggleButton = findViewById(R.id.toggleButton);

        buttonInsert = findViewById(R.id.buttonInsert);

        progressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    isvet="1";

                }
                else
                {

                    isvet=null;
                }
            }
        });

        if(fAuth.getCurrentUser()!=null){
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            Toast.makeText(admin.this, "You are logged in ", Toast.LENGTH_SHORT).show();
            finish();
        }



        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameTxt = userFirstName.getText().toString();
                String surnameTxt = userLastname.getText().toString();
                String usernameTxt = username.getText().toString();
                String passwordTxt = userPassword.getText().toString();

                fstore=FirebaseFirestore.getInstance();

                if(TextUtils.isEmpty(usernameTxt))
                {
                    username.setError("Email required");
                    return;
                }
                if(TextUtils.isEmpty(passwordTxt))
                
                {
                    userPassword.setError("Password required");
                    return;
                }
                if(passwordTxt.length()< 6)
                {
                    userPassword.setError("Password must be >=6 Characters ");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

               fAuth.createUserWithEmailAndPassword(usernameTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(admin.this,"user created",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userID);

                            Map<String,Object> user=new HashMap<>();
                            user.put("fname",firstNameTxt);
                            user.put("lname",surnameTxt);
                            user.put("email",usernameTxt);

                            user.put("isvet",isvet);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                  Log.d(TAG,"On Success:user Profile is created for"+userID);
                                }
                            });
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            myRef.child(firstNameTxt+" "+surnameTxt).child("first Name").setValue(usernameTxt);

                            checkUserAccesslevel(userID);

                            progressBar.setVisibility(View.GONE);
                        }
                        else
                        {
                            Toast.makeText(admin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

              }

        });
    }

    private void checkUserAccesslevel(String uid)
    {
        DocumentReference df=fstore.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.getString("isvet")!=null){
                    Log.d("TAG","onSuccess"+documentSnapshot.getData());
                    startActivity(new Intent(getApplicationContext(),vetmain.class));
                    finish();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),farmer.class));
                    finish();
                }
            }
        });
    }
}