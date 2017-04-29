package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Amr on 24/04/2017.
 */
@IgnoreExtraProperties
public class User extends Person {


    public User(String id, String name, String email, String phone, String address) {
        super(id, name, email, phone, address);
    }
}