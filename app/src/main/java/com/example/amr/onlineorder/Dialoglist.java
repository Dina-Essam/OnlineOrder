package com.example.amr.onlineorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialoglist extends AppCompatActivity {

    Button edit, delete;
    String check;
    String id_cat, name_cat, color_cat;
    String id_pro, name_pro, price_pro, image_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoglist);

        edit = (Button) findViewById(R.id.edit);
        delete = (Button) findViewById(R.id.delete);

        Bundle extras = getIntent().getExtras();
        check = extras.getString("bundlee");

        id_cat = extras.getString("i_cat");
        name_cat = extras.getString("n_cat");
        color_cat = extras.getString("c_cat");

        id_pro = extras.getString("i_pro");
        name_pro = extras.getString("n_pro");
        price_pro = extras.getString("p_pro");
        image_pro = extras.getString("im_pro");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.equals("category")) {

                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("iD_cat", id_cat);
                    dataBundle.putString("na_cat", name_cat);
                    dataBundle.putString("col_cat", color_cat);
                    Intent i = new Intent(Dialoglist.this, EditCategory.class);
                    i.putExtras(dataBundle);
                    startActivity(i);
                    finish();

                } else if (check.equals("product")) {

                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("iD_pro", id_pro);
                    dataBundle.putString("na_pro", name_pro);
                    dataBundle.putString("pri_pro", price_pro);
                    dataBundle.putString("img_pro", image_pro);
                    Intent i = new Intent(Dialoglist.this, EditProduct.class);
                    i.putExtras(dataBundle);
                    startActivity(i);
                    finish();

                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check.equals("category")) {

                    RemoveC(id_cat);
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (check.equals("product")) {

                    RemoveP(id_pro);
                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    private void RemoveC(String id) {

        DatabaseReference mFirebaseDatabase;

        FirebaseDatabase mFirebaseInstance;

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("categoriesAdmin");

        mFirebaseDatabase.child(id).child("id").removeValue();

        mFirebaseDatabase.child(id).child("name").removeValue();

        mFirebaseDatabase.child(id).child("color").removeValue();

        mFirebaseDatabase.child(id).child("admin_id").removeValue();

    }

    private void RemoveP(String id) {

        DatabaseReference mFirebaseDatabase;

        FirebaseDatabase mFirebaseInstance;

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("productsC");

        mFirebaseDatabase.child(id).child("id").removeValue();

        mFirebaseDatabase.child(id).child("name").removeValue();

        mFirebaseDatabase.child(id).child("price").removeValue();

        mFirebaseDatabase.child(id).child("url").removeValue();

        mFirebaseDatabase.child(id).child("category_id").removeValue();

    }
}
