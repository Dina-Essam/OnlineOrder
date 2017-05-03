package com.example.amr.onlineorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class show_products_of_order extends AppCompatActivity {

    ArrayList<Product> products=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_of_order);


        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        products = (ArrayList<Product>) bundle.getSerializable("PRODUCTS");








    }
}
