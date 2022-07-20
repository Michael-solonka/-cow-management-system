package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class user extends AppCompatActivity {
    private TextView nouser;
    ListView userlist;
    ArrayList<String> list=new ArrayList<>();
    DatabaseReference reff;
    FirebaseFirestore fstore;
    long maxid=0;
    FirebaseUser fire;
    String uid;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        nouser=findViewById(R.id.nousers);
        userlist=findViewById(R.id.userlist);
        fire = FirebaseAuth.getInstance().getCurrentUser();
        uid=fire.getUid();
        pd = new ProgressDialog(user.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://my-application-e5660-default-rtdb.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("" + error);
            }

    });

        RequestQueue rQueue = Volley.newRequestQueue(user.this);
        rQueue.add(request);

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith=list.get(position);
                startActivity(new Intent(user.this,messagereply.class));
            }
        });
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    list.add(key);
                }

                maxid++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(maxid <=1){
            nouser.setVisibility(View.VISIBLE);
            userlist.setVisibility(View.GONE);
        }
        else{
            nouser.setVisibility(View.GONE);
            userlist.setVisibility(View.VISIBLE);
            userlist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        }

        pd.dismiss();
    }
}