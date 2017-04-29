package com.example.amr.onlineorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectHowToDo extends AppCompatActivity {

    Button edit, delete;
    String id_cat, name_cat, color_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_how_to_do);

        edit = (Button) findViewById(R.id.editcat);
        delete = (Button) findViewById(R.id.deletecat);

        Bundle extras = getIntent().getExtras();
        id_cat = extras.getString("id_cat");
        name_cat = extras.getString("name_cat");
        color_cat = extras.getString("color_cat");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dataBundle = new Bundle();
                dataBundle.putString("iD_cat", id_cat);
                dataBundle.putString("na_cat", name_cat);
                dataBundle.putString("col_cat", color_cat);
                Intent i = new Intent(SelectHowToDo.this, EditCategory.class);
                i.putExtras(dataBundle);
                startActivity(i);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RemoveCat(id_cat);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void RemoveCat(String id) {

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("categoriesAdmin");

        mFirebaseDatabase.child(id).child("id").removeValue();

        mFirebaseDatabase.child(id).child("name").removeValue();

        mFirebaseDatabase.child(id).child("color").removeValue();

        mFirebaseDatabase.child(id).child("admin_id").removeValue();

    }
}
