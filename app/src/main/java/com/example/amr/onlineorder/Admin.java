package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Amr on 24/04/2017.
 */
@IgnoreExtraProperties
public class Admin extends Person {

    String url;

    public Admin(String id, String name, String email, String phone, String address, String url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getID() {
        return id;
    }


}
