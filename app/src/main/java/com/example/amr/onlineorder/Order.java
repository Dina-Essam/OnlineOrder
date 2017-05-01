package com.example.amr.onlineorder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dina on 4/27/2017.
 */

public class Order implements Serializable {

    String userID;
    String id;
    String brand_id;
    Double totalPrice;
    String state;
    ArrayList<Product> items;

    public Order() {

    }

    public Order(String userID, String id, String brand_id, String state, ArrayList<Product> items) {
        this.id = id;
        this.userID = userID;
        this.brand_id = brand_id;
        this.state = state;
        this.items = items;
        totalPrice = 0.0;

        for (int i = 0; i < items.size(); i++) {
            totalPrice += Integer.parseInt(items.get(i).getPrice());
        }


    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public String getState() {
        return state;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
