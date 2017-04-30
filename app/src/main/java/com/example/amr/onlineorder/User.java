package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class User extends Person implements Serializable {

    public User(String id, String name, String email, String phone, String address) {
        super(id, name, email, phone, address);
    }
}