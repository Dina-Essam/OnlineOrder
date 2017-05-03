package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineOrders extends AppCompatActivity {


    String adm_id;
    private ProgressDialog progressDialog;
    DatabaseReference mDataRef;
    ArrayList<Order> orderlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        adm_id = extras.getString("admi_id");
        FirebaseDatabase database = FirebaseDatabase.getInstance();




        /**
         * fill Orders
         *
         **/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDataRef = database.getReference();
        mDataRef.child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                orderlist.clear();
                Order oneOrder;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                try {
                    for (DataSnapshot child : children) {

                        oneOrder = child.getValue(Order.class);
                        if (adm_id.equals(oneOrder.brand_id)) {
                            orderlist.add(oneOrder);
                        }

                    }


                } catch (Exception e) {
                    Toast.makeText(OnlineOrders.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

}
