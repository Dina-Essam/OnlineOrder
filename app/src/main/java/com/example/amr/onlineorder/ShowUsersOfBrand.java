package com.example.amr.onlineorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowUsersOfBrand extends AppCompatActivity {

    String adm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users_of_brand);

        Bundle extras = getIntent().getExtras();
        adm_id = extras.getString("admin_id");






    }
}
