package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class addcows extends AppCompatActivity {
    private EditText cowid,DOB,gender,parentid,username;
    private Button button3,button,delete;
    FirebaseUser fire;
    String uid;
    DatabaseReference reff;
    long maxid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcows);

        cowid=findViewById(R.id.cowid);
        DOB=findViewById(R.id.DOB);
        gender=findViewById(R.id.gender);
        parentid=findViewById(R.id.parentid);
        button=findViewById(R.id.button);
        delete=findViewById(R.id.delete);

         fire = FirebaseAuth.getInstance().getCurrentUser();
         uid=fire.getUid();
        button3=findViewById(R.id.button3);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        reff=FirebaseDatabase.getInstance().getReference().child("cows").child(uid);
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

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        addcows.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate=dayOfMonth+"/"+(month+1)+"/"+year;
                        DOB.setText(sDate);

                    }
                },year,month,day
                );

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cowidTxt = cowid.getText().toString();
                String DOBTxt = DOB.getText().toString();
                String genderTxt = gender.getText().toString();
                String parentidTxt = parentid.getText().toString();



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("cows");
                cowshelper l= new cowshelper(cowidTxt,DOBTxt,genderTxt,parentidTxt);

                myRef.child(uid).child(cowidTxt).setValue(l);
                Toast.makeText(addcows.this,"Cow has been added",Toast.LENGTH_SHORT).show();

                cowid.getText().clear();
                DOB.getText().clear();
                gender.getText().clear();
                parentid.getText().clear();
                Toast.makeText(addcows.this,"Add another cow",Toast.LENGTH_SHORT).show();


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cowidTxt = cowid.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("cows").child(uid);
                myRef.child(cowidTxt).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(addcows.this,"record"+ cowidTxt+ "has been deleted ",Toast.LENGTH_SHORT).show();
                            cowid.getText().clear();
                        }
                        else
                        {
                            Toast.makeText(addcows.this,"FAILED TO DELETE ",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addcows.this);
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