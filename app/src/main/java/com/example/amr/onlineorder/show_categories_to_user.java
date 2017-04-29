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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories_to_user);

        Bundle bundle = new Bundle();
        bundle=getIntent().getExtras();
        Brandinit=(Admin)bundle.getSerializable("BRAND");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        viewAllBrands = (ListView) findViewById(R.id.listview_categories);
        categoryList=new ArrayList<>();


        /**
         * Fill List from Brand
         */




        databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                categoryList.clear();
                for (DataSnapshot child : children)
                {

                    String id = child.child("id").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String admin_id = child.child("admin_id").getValue().toString();
                    String color = child.child("color").getValue().toString();
                    Category c = new Category(id,name,color,admin_id);

                    if(Brandinit.getId()== c.Admin_id) {

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
                 * h8yar l userlogin 34an ywdeni 3l category lma tt3ml
                 */
                Intent GOTOproduct = new Intent(show_categories_to_user.this, show_brands_to_user.class);

                Bundle bundle = new Bundle();
                //bundle.putSerializable("USER", Theone);
                bundle.putSerializable("CATEGORY",categoryList.get(position));
                GOTOproduct.putExtras(bundle);
                startActivity(GOTOproduct);
            }
        });


    }


    /**
     * Adapter
     */
    class CustomAdapter extends BaseAdapter
    {
        ArrayList<Category> categoryArrayList=new ArrayList<>();

        CustomAdapter(ArrayList<Category> listofbrands)
        {
            this.categoryArrayList =listofbrands;
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

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater=getLayoutInflater();
            View view1=linflater.inflate(R.layout.row_category,null);

            /**
             * blwen l category
             */
            LinearLayout layoutcat=(LinearLayout)view1.findViewById(R.id.layoutcat);
            layoutcat.setBackground(Drawable.createFromPath(categoryArrayList.get(position).getColor()));

            TextView bname=(TextView)view1.findViewById(R.id.category_name_show_user);


            bname.setText(categoryArrayList.get(position).getName());


            return view1;
        }
    }




}
