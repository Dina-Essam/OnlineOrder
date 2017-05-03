package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowUsersOfBrand extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    String adm_id;
    private ProgressDialog progressDialog;
    ArrayList<String> dataids_users, datanames_users;
    DatabaseReference databaseReference1, databaseReference2;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users_of_brand);

        Bundle extras = getIntent().getExtras();
        adm_id = extras.getString("admi_id");

      //  Toast.makeText(ShowUsersOfBrand.this, adm_id, Toast.LENGTH_SHORT).show();

        dataids_users = new ArrayList<>();
        datanames_users = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listview_users_admin);
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        databaseReference1 = database1.getReference();
        databaseReference1.child("Order").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                dataids_users.clear();
                for (DataSnapshot child : children) {
                    String brand_id = child.child("brand_id").getValue().toString();
                    String userID = child.child("userID").getValue().toString();

                    if (adm_id.equals(brand_id) && !dataids_users.contains(userID)) {
                        dataids_users.add(userID);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        databaseReference2 = database2.getReference();
        databaseReference2.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                datanames_users.clear();
                for (DataSnapshot child : children) {
                    String _id = child.child("id").getValue().toString();
                    String names = child.child("name").getValue().toString();

                    if (dataids_users.contains(_id)) {
                        datanames_users.add(names);
                    }
                }
                adapter = new ArrayAdapter<String>(ShowUsersOfBrand.this, android.R.layout.simple_list_item_1, datanames_users);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Toast.makeText(ShowUsersOfBrand.this, dataids_users.get(arg2), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
