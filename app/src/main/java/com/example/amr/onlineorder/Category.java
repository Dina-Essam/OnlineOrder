package com.example.amr.onlineorder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Category implements Serializable {
    String id;
    String name;
    String color;
    String Admin_id;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public Category() {
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("categoriesAdmin");

    }

    public Category(String id, String name, String color, String admin_id) {
        this.id = id;
        this.name = name;
        this.color = color;
        Admin_id = admin_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdmin_id() {
        return Admin_id;
    }

    public String getColor() {
        return color;
    }

    // function el update fel firebase bmsek el root w b7ded el id w babda2 a update
    public void updateCat(String name, String color, String id) {

        mFirebaseDatabase.child(id).child("name").setValue(name);
        mFirebaseDatabase.child(id).child("color").setValue(color);

    }

    public void DeleteCat(String id) {
        mFirebaseDatabase.child(id).removeValue();
    }





}
