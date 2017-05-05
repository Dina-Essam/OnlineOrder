package com.example.amr.onlineorder;

import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.amr.onlineorder.R.drawable.list;

@IgnoreExtraProperties
public class Admin extends Person implements Serializable {

    String url;

    public Admin() {

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Admin(String id, String name, String email, String phone, String address, String url) {
        super(id, name, email, phone, address);
        this.url = url;
    }



    public ArrayList<Category> CategoriesofBrand(final String ID , final ListView viewAllBrands,final show_categories_to_user show)
    {

    final ArrayList<Category> categoryList = new ArrayList<>();
    DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    databaseReference = database.getReference();
    databaseReference.child("categoriesAdmin").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> children = dataSnapshot.getChildren();
            categoryList.clear();
            for (DataSnapshot child : children) {
                String uid = child.getKey();
                String name = child.child("name").getValue().toString();
                String color = child.child("color").getValue().toString();
                String admin_id = child.child("admin_id").getValue().toString();
                Category c = new Category(uid, name, color, admin_id);

                if (ID.equals(admin_id)) {
                    categoryList.add(c);
                }
            }

            show_categories_to_user.CustomAdapter myAdapter = show.new CustomAdapter(categoryList);
            viewAllBrands.setAdapter(myAdapter);


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


    return categoryList;
}







}
