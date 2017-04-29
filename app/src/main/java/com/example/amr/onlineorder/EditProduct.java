package com.example.amr.onlineorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EditProduct extends AppCompatActivity {

    String id_pro, name_pro, price_pro, image_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Bundle extras = getIntent().getExtras();
        id_pro = extras.getString("iD_pro");
        name_pro = extras.getString("na_pro");
        price_pro = extras.getString("pri_pro");
        image_pro = extras.getString("img_pro");

        Toast.makeText(EditProduct.this, id_pro, Toast.LENGTH_SHORT).show();

    }
}
