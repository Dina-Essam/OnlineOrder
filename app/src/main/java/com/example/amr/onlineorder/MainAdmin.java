package com.example.amr.onlineorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainAdmin extends AppCompatActivity {

    Button CMS, OnlineOrder, ShowUser, Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        CMS = (Button) findViewById(R.id.cms);
        OnlineOrder = (Button) findViewById(R.id.onlineorder);
        ShowUser = (Button) findViewById(R.id.showuser);
        Logout = (Button) findViewById(R.id.logout);

        CMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainAdmin.this, ShowCategories.class);
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
