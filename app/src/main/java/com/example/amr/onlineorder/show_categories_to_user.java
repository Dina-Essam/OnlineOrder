package com.example.amr.onlineorder;

import android.content.Intent;
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

    ListView viewAllBrands;
    Admin Brandinit;
    DatabaseReference databaseReference;
    ArrayList<Category> categoryList;
<<<<<<< HEAD

=======
>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories_to_user);

        Bundle bundle = new Bundle();
<<<<<<< HEAD
        bundle = getIntent().getExtras();
        Brandinit = (Admin) bundle.getSerializable("BRAND");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        viewAllBrands = (ListView) findViewById(R.id.listview_categories);
        categoryList = new ArrayList<>();
=======
        bundle=getIntent().getExtras();
        Brandinit=(Admin)bundle.getSerializable("BRAND");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        viewAllBrands = (ListView) findViewById(R.id.listview_categories);
        categoryList=new ArrayList<>();
>>>>>>> origin/master


        /**
         * Fill List from Brand
         */


<<<<<<< HEAD
=======


>>>>>>> origin/master
        databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                categoryList.clear();
<<<<<<< HEAD
                for (DataSnapshot child : children) {
=======
                for (DataSnapshot child : children)
                {
>>>>>>> origin/master

                    String id = child.child("id").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String admin_id = child.child("admin_id").getValue().toString();
                    String color = child.child("color").getValue().toString();
<<<<<<< HEAD
                    Category c = new Category(id, name, color, admin_id);

                    if (Brandinit.getId() == c.Admin_id) {
=======
                    Category c = new Category(id,name,color,admin_id);

                    if(Brandinit.getId()== c.Admin_id) {
>>>>>>> origin/master

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


<<<<<<< HEAD
=======

>>>>>>> origin/master
        viewAllBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                /** send it to product activity
                 * h8yar l userlogin 34an ywdeni 3l category lma tt3ml
                 */
                Intent GOTOproduct = new Intent(show_categories_to_user.this, show_brands_to_user.class);

                Bundle bundle = new Bundle();
                //bundle.putSerializable("USER", Theone);
<<<<<<< HEAD
                bundle.putSerializable("CATEGORY", categoryList.get(position));
=======
                bundle.putSerializable("CATEGORY",categoryList.get(position));
>>>>>>> origin/master
                GOTOproduct.putExtras(bundle);
                startActivity(GOTOproduct);
            }
        });


    }


    /**
     * Adapter
     */
<<<<<<< HEAD
    class CustomAdapter extends BaseAdapter {
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        CustomAdapter(ArrayList<Category> listofbrands) {
            this.categoryArrayList = listofbrands;
        }

=======
    class CustomAdapter extends BaseAdapter
    {
        ArrayList<Category> categoryArrayList=new ArrayList<>();

        CustomAdapter(ArrayList<Category> listofbrands)
        {
            this.categoryArrayList =listofbrands;
        }
>>>>>>> origin/master
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
<<<<<<< HEAD
            LayoutInflater linflater = getLayoutInflater();
            View view1 = linflater.inflate(R.layout.row_category, null);
=======
            LayoutInflater linflater=getLayoutInflater();
            View view1=linflater.inflate(R.layout.row_category,null);
>>>>>>> origin/master

            /**
             * blwen l category
             */
<<<<<<< HEAD
            LinearLayout layoutcat = (LinearLayout) view1.findViewById(R.id.layoutcat);
            layoutcat.setBackground(Drawable.createFromPath(categoryArrayList.get(position).getColor()));

            TextView bname = (TextView) view1.findViewById(R.id.category_name_show_user);
=======
            LinearLayout layoutcat=(LinearLayout)view1.findViewById(R.id.layoutcat);
            layoutcat.setBackground(Drawable.createFromPath(categoryArrayList.get(position).getColor()));

            TextView bname=(TextView)view1.findViewById(R.id.category_name_show_user);
>>>>>>> origin/master


            bname.setText(categoryArrayList.get(position).getName());


            return view1;
        }
    }


<<<<<<< HEAD
=======


>>>>>>> origin/master
}
