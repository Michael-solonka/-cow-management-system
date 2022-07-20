package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class messagereply extends AppCompatActivity {
    private ListView listView52;
    ArrayList<String> list=new ArrayList<>();
    DatabaseReference reference1,reference2;
    Button buttonreply;
    FirebaseUser fire;
    String username;
    String uid;
    EditText messageArea1;
    LinearLayout layout;
    ImageView sendButton;

    ScrollView scrollView;
    FirebaseFirestore fstore;
    long maxid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagereply);

        //listView52=(ListView)findViewById(R.id.listView52);

        layout = (LinearLayout)findViewById(R.id.layout1);
        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();
        fstore= FirebaseFirestore.getInstance();
        sendButton = (ImageView)findViewById(R.id.sendButton);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        messageArea1=findViewById(R.id.messageArea);
        reference1= FirebaseDatabase.getInstance().getReference().child("message");
        reference2= FirebaseDatabase.getInstance().getReference().child("message");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea1.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("chatwith", UserDetails.chatWith);
                    reference1.push().setValue(map);
                   // reference2.push().setValue(map);
                }
                messageArea1.getText().clear();
            }

        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String message = snapshot.child("message").getValue().toString();
                String userName = snapshot.child("user").getValue().toString();
                String chatwith= snapshot.child("chatwith").getValue().toString();
                if (userName.equals(UserDetails.username)&&chatwith.equals(UserDetails.chatWith)||userName.equals(UserDetails.chatWith)&&chatwith.equals(UserDetails.username)) {
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:-\n" + message, 1);
                    } else {
                        addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    }
                }

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(messagereply.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);

    }
}

