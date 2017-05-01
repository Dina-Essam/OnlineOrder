package com.example.amr.onlineorder;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_brands_to_user extends AppCompatActivity {


    ListView viewAllBrands;
    DatabaseReference databaseReference;
    ArrayList<Admin> Brands;
    Toolbar toolbar_for_user;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_brands_to_user);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference();
        viewAllBrands = (ListView) findViewById(R.id.listview_brands);
        toolbar_for_user = (Toolbar) findViewById(R.id.toolbar_for_user);
        setSupportActionBar(toolbar_for_user);
        /**
         * Function fill list of brands from database
         */

        FillBrandsList();


        /**
         * When Click On any Brand
         */
        viewAllBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                /** send it to category activity
                 * h8yar l userlogin 34an ywdeni 3l category lma tt3ml
                 */
                Intent GOTOCategory = new Intent(show_brands_to_user.this, show_categories_to_user.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("BRAND", Brands.get(position));
                GOTOCategory.putExtras(bundle);
                startActivity(GOTOCategory);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.user_orders) {

            /**
             * hn2lo l layout h3red feha l products bs hdelha l user id bundle
             */
        } else if (item.getItemId() == R.id.logout_user) {

            firebaseAuth.signOut();
            Intent logout = new Intent(show_brands_to_user.this, StartActivity.class);
            startActivity(logout);
        }


        return super.onOptionsItemSelected(item);
    }


    private void FillBrandsList() {


        /**
         * Fill from Database
         */


        Brands = new ArrayList<>();

        databaseReference.child("admins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Brands.clear();
                for (DataSnapshot child : children) {
                    String id = child.child("id").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String email = child.child("email").getValue().toString();
                    String phone = child.child("phone").getValue().toString();
                    String address = child.child("address").getValue().toString();
                    String url = child.child("url").getValue().toString();
                    Admin c = new Admin(id, name, email, phone, address, url);

                    Brands.add(c);

                }

                CustomAdapter myAdapter = new CustomAdapter(Brands);
                viewAllBrands.setAdapter(myAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    /**
     * Adapter
     */
    class CustomAdapter extends BaseAdapter {
        ArrayList<Admin> listofbrands;

        CustomAdapter(ArrayList<Admin> listofbrands) {
            this.listofbrands = listofbrands;
        }

        @Override
        public int getCount() {
            return listofbrands.size();
        }

        @Override
        public Object getItem(int position) {
            return listofbrands.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.row_brands, null);

            TextView bname = (TextView) view1.findViewById(R.id.brand_name_show_user);
            ImageView bimage = (ImageView) view1.findViewById(R.id.brand_image_show_user);

            bname.setText(listofbrands.get(position).getName());
            Glide.with(getApplicationContext()).load(listofbrands.get(position).getUrl()).into(bimage);

            return view1;
        }
    }


}
