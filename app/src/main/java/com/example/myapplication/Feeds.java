package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Feeds extends AppCompatActivity {
    private EditText feedid,feedname,amount;
    private Button addfeeds,button,delete1;
    FirebaseUser fire;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        feedid=findViewById(R.id.feedid);
        feedname=findViewById(R.id.feedname);
        amount=findViewById(R.id.amount);
        delete1=findViewById(R.id.delete1);

        addfeeds=findViewById(R.id.addfeeds);
        button=findViewById(R.id.button);

        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();

        addfeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedidTxt = feedid.getText().toString();
                String feednameTxt = feedname.getText().toString();
                String amountTxt = amount.getText().toString();
                String usernameTxt= uid;


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("feeds");
                feedhelper l= new feedhelper(feedidTxt,feednameTxt,amountTxt,usernameTxt);

                myRef.child(usernameTxt).child(feedidTxt).setValue(l);
                Toast.makeText(Feeds.this,"Feed has been added",Toast.LENGTH_SHORT).show();
                feedid.getText().clear();
                feedname.getText().clear();
                amount.getText().clear();
                Toast.makeText(Feeds.this,"Add/update Feeds ",Toast.LENGTH_SHORT).show();

            }
        });
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedidTxt = feedid.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("feeds").child(uid);
                myRef.child(feedidTxt).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Feeds.this,"record "+ feedidTxt + " has been deleted ",Toast.LENGTH_SHORT).show();
                            feedid.getText().clear();
                        }
                        else
                        {
                            Toast.makeText(Feeds.this,"FAILED TO DELETE ",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Feeds.this);
                builder.setTitle("Confirmation!").setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();

                Intent myIntent = getIntent();
                String string = myIntent.getStringExtra("message");

            }
        });
    }
}