package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Category implements Serializable {
    String id;

    public String getId() {
        return id;
    }

    String name;
    String color;
    String Admin_id;

    public Category() {
    }

    public Category(String id, String name, String color, String admin_id) {
        this.id = id;
        this.name = name;
        this.color = color;
        Admin_id = admin_id;
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

}
