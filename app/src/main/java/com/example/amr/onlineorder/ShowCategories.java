package com.example.amr.onlineorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCategories extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ArrayList<Category> data;
    DatabaseReference databaseReference;
    CategoriesAdapter categoriesAdapter;
    ListView lv;
    String adm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listview_cat);
        progressDialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        adm_id = extras.getString("admin_id");

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                data.clear();
                for (DataSnapshot child : children) {
                    String uid = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String color = child.child("color").getValue().toString();
                    String admin_id = child.child("admin_id").getValue().toString();
                    Category c = new Category(uid, name, color, admin_id);

                    if (adm_id.equals(admin_id)) {
                        data.add(c);
                    }
                }
                categoriesAdapter = new CategoriesAdapter(ShowCategories.this, data);
                lv.setAdapter(categoriesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //   Toast.makeText(ShowCategories.this, data.get(position).getName(), Toast.LENGTH_SHORT).show();

                Bundle dataBundle = new Bundle();
                dataBundle.putString("cat_id", data.get(position).getId());
                dataBundle.putString("cat_color", data.get(position).getColor());
                dataBundle.putString("adminnn_id", adm_id);
                Intent i = new Intent(ShowCategories.this, ShowProducts.class);
                i.putExtras(dataBundle);
                startActivity(i);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           final int index, long arg3) {

//                Bundle dataBundle = new Bundle();
//                dataBundle.putString("iD_cat", data.get(index).getId());
//                dataBundle.putString("na_cat", data.get(index).getName());
//                dataBundle.putString("col_cat", data.get(index).getColor());
//                Intent i = new Intent(ShowCategories.this, EditCategory.class);
//                i.putExtras(dataBundle);
//                startActivity(i);
                Bundle dataBundle = new Bundle();
                dataBundle.putString("bundlee", "category");

                dataBundle.putString("i_cat", data.get(index).getId());
                dataBundle.putString("n_cat", data.get(index).getName());
                dataBundle.putString("c_cat", data.get(index).getColor());

                Intent i = new Intent(ShowCategories.this, Dialoglist.class);
                i.putExtras(dataBundle);
                startActivity(i);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("adminn_id", adm_id);
                Intent i = new Intent(ShowCategories.this, AddCategory.class);
                i.putExtras(dataBundle);
                startActivity(i);
            }
        });
    }


}