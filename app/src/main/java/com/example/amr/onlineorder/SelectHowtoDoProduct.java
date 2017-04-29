package com.example.amr.onlineorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectHowtoDoProduct extends AppCompatActivity {

    Button edit, delete;
    String id_pro, name_pro, price_pro, image_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_howto_do_product);

        Bundle extras = getIntent().getExtras();
        id_pro = extras.getString("id_pro");
        name_pro = extras.getString("name_pro");
        price_pro = extras.getString("price_pro");
        image_pro = extras.getString("image_pro");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dataBundle = new Bundle();
                dataBundle.putString("iD_pro", id_pro);
                dataBundle.putString("na_pro", name_pro);
                dataBundle.putString("pri_pro", price_pro);
                dataBundle.putString("img_pro", image_pro);
                Intent i = new Intent(SelectHowtoDoProduct.this, EditProduct.class);
                i.putExtras(dataBundle);
                startActivity(i);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RemovePro(id_pro);
                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });
    }

    public void RemovePro(String id) {

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("productsC");

        mFirebaseDatabase.child(id).child("id").removeValue();

        mFirebaseDatabase.child(id).child("name").removeValue();

        mFirebaseDatabase.child(id).child("price").removeValue();

        mFirebaseDatabase.child(id).child("url").removeValue();

        mFirebaseDatabase.child(id).child("category_id").removeValue();

    }
}
