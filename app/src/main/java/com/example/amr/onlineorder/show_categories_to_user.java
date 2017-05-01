package com.example.amr.onlineorder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class show_categories_to_user extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ListView viewAllBrands;
    Admin Brandinit;
    DatabaseReference databaseReference;
    ArrayList<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories_to_user);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Brandinit = (Admin) bundle.getSerializable("BRAND");
        categoryList = new ArrayList<>();
        viewAllBrands = (ListView) findViewById(R.id.listview_categories);
        progressDialog = new ProgressDialog(this);


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();



        /**
         * Fill List from Brand
         */


        databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                categoryList.clear();
                for (DataSnapshot child : children)
                {
                    String uid = child.getKey();
                    String name = child.child("name").getValue().toString();
                    String color = child.child("color").getValue().toString();
                    String admin_id = child.child("admin_id").getValue().toString();
                    Category c = new Category(uid, name, color, admin_id);

                    if (Brandinit.getId().equals(admin_id))
                    {
                        categoryList.add(c);
                    }
                }

                CustomAdapter myAdapter = new CustomAdapter(categoryList);
                viewAllBrands.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewAllBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                /** send it to product activity
                 * h8yar l userlogin 34an ywdeni 3l Products lma tt3ml
                 */
                Intent GOTOproduct = new Intent(show_categories_to_user.this, show_products_to_user.class);

                Bundle bundle = new Bundle();
                //bundle.putSerializable("USER", Theone);
                bundle.putSerializable("CATEGORY", categoryList.get(position));
                GOTOproduct.putExtras(bundle);
                startActivity(GOTOproduct);
            }
        });


    }


    /**
     * Adapter
     */
    class CustomAdapter extends BaseAdapter {
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        CustomAdapter(ArrayList<Category> listofbrands) {
            this.categoryArrayList = listofbrands;
        }

        @Override
        public int getCount() {
            return categoryArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.row_category, null);

            /**
             * blwen l category w bzher 2sm l Product
             */
            LinearLayout layoutcat = (LinearLayout) view1.findViewById(R.id.layoutcat);
            layoutcat.setBackgroundColor(Color.parseColor(categoryArrayList.get(position).getColor()));
            TextView bname = (TextView) view1.findViewById(R.id.category_name_show_user);
            bname.setText(categoryArrayList.get(position).getName());


            return view1;
        }
    }


}
