package com.example.amr.onlineorder;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {
    String id;
    String name;
    String price;
    String url;
    String category_id;

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

}
