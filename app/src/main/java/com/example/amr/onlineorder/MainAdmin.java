package com.example.amr.onlineorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainAdmin extends AppCompatActivity {

    Button CMS, OnlineOrder, ShowUser, Logout;
    DatabaseReference databaseReference;
    ArrayList<String> id_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        CMS = (Button) findViewById(R.id.cms);
        OnlineOrder = (Button) findViewById(R.id.onlineorder);
        ShowUser = (Button) findViewById(R.id.showuser);
        Logout = (Button) findViewById(R.id.logout);
        id_admin = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("admins").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                id_admin.clear();
                for (DataSnapshot child : children) {
                    String uid = child.getKey();
                    String email = child.child("email").getValue().toString();

                    if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(email)) {
                        id_admin.add(uid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dataBundle = new Bundle();
                dataBundle.putString("admin_id", id_admin.get(0));
                Intent i = new Intent(MainAdmin.this, ShowCategories.class);
                i.putExtras(dataBundle);
                startActivity(i);

            }
        });
        OnlineOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);

            }
        });
    }

}
