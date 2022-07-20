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

public class milk extends AppCompatActivity {
    private EditText CowId,days,Aomilk;
    private Button addmilk,button;
    FirebaseUser fire;
    String uid;
    DatabaseReference reff;
    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk);

        CowId=findViewById(R.id.CowId);
        days=findViewById(R.id.days);
        Aomilk=findViewById(R.id.Aomilk);
        addmilk=findViewById(R.id.addmilk);
        button=findViewById(R.id.button);

        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        reff=FirebaseDatabase.getInstance().getReference().child("milk").child(uid);
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
        days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        milk.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDate=dayOfMonth+"/"+(month+1)+"/"+year;
                        days.setText(sDate);

                    }
                },year,month,day
                );

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        addmilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cow=CowId.getText().toString();
                String day=days.getText().toString();
                Integer amount=Integer.parseInt(Aomilk.getText().toString());


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("milk");
                milkhelper l= new milkhelper(cow,day,amount);

                myRef.child(uid).child(String.valueOf(maxid+1)).setValue(l);
                Toast.makeText(milk.this,"Milk record has been added",Toast.LENGTH_SHORT).show();
                CowId.getText().clear();
                days.getText().clear();
                Aomilk.getText().clear();
                Toast.makeText(milk.this,"Add new Milk record ",Toast.LENGTH_SHORT).show();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(milk.this);
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