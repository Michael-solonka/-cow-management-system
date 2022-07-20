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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class addtreatment extends AppCompatActivity {
    private EditText cow_id,date,meds,aom;
    private Button addmeds,button;
    FirebaseUser fire;
    String uid;
    DatabaseReference reff;
    long max=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtreatment);

        cow_id=findViewById(R.id.cow_id);
        date=findViewById(R.id.date);
        meds=findViewById(R.id.meds);
        aom=findViewById(R.id.aom);
        addmeds=findViewById(R.id.addmeds);
        button=findViewById(R.id.button);
        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        reff=FirebaseDatabase.getInstance().getReference().child("treatment").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    max=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        addtreatment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate=dayOfMonth+"/"+(month+1)+"/"+year;
                        date.setText(sDate);

                    }
                },year,month,day
                );

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        addmeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cow=cow_id.getText().toString();
                String day=date.getText().toString();
                String medication=meds.getText().toString();
                String amount=aom.getText().toString();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("treatment");
                treatmenthelper l= new treatmenthelper(cow,day,medication,amount);

                myRef.child(uid).child(String.valueOf(max+1)).setValue(l);
                Toast.makeText(addtreatment.this,"Treatment has been added",Toast.LENGTH_SHORT).show();
                cow_id.getText().clear();
                date.getText().clear();
                meds.getText().clear();
                aom.getText().clear();
                Toast.makeText(addtreatment.this,"Add another Treatment ",Toast.LENGTH_SHORT).show();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(addtreatment.this);
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