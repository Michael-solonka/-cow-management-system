package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    private EditText loginusername,loginpassword;

    private Button Login,button10;

    private String selectedRb = null;
    String userId;

    private ProgressBar progressBar2;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginusername = findViewById(R.id.loginusername);
        loginpassword = findViewById(R.id.loginpassword);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.Login);
        button10=findViewById(R.id.singUp);
        fstore= FirebaseFirestore.getInstance();





        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginusername.getText().toString();
                String pass = loginpassword.getText().toString();

                if (TextUtils.isEmpty(user)) {
                    loginusername.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    loginpassword.setError("Password required");
                    return;
                }
                if (pass.length() < 4) {
                    loginpassword.setError("Password must be >=6 Characters ");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userId=fAuth.getCurrentUser().getUid();
                            //UserDetails.username = user;
                            checkUserAccesslevel(userId);
                            Toast.makeText(MainActivity.this, "Logged in successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }

                });



            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),admin.class);
                startActivity(i);
            }
        });
    }


    private void checkUserAccesslevel(String uid)
    {
        DocumentReference df=fstore.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess"+documentSnapshot.getData());
                if(documentSnapshot.getString("isvet")!=null){
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