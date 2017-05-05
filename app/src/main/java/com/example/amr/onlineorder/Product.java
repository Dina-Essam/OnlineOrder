package com.example.amr.onlineorder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Product implements Serializable {
    String id;
    String name;
    String price;
    String url;
    String category_id;


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public Product() {

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("productsC");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getUrl() {
        return url;
    }

    public Product(String id, String name, String price, String url, String category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
        this.category_id = category_id;
    }
    // function el update fel firebase bmsek el root w b7ded el id w babda2 a update
    // overload

    public void updatePro(String name, String price, String url, String id) {

        mFirebaseDatabase.child(id).child("name").setValue(name);
        mFirebaseDatabase.child(id).child("price").setValue(price);
        mFirebaseDatabase.child(id).child("url").setValue(url);

    }

    public void updatePro(String name, String price, String id) {

        mFirebaseDatabase.child(id).child("name").setValue(name);
        mFirebaseDatabase.child(id).child("price").setValue(price);

    }
    public void DeletePro(String id)
    {
        mFirebaseDatabase.child(id).removeValue();
    }
}
