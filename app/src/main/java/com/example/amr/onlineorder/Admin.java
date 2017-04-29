package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Amr on 24/04/2017.
 */
@IgnoreExtraProperties
public class Admin extends Person implements Serializable {

    String url;


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
}
